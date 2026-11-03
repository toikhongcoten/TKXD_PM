package starter.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import starter.InMemoryMockDB;
import starter.LocModel;
import starter.LocPresenter;
import starter.LocResultDTO;
import starter.LocService;
import starter.PresentAble;
import starter.StoreAble;

public class LocServiceTest {

    @Test
    @DisplayName("Khi lọc đa tiêu chí thành công với vai trò CUSTOMER")
    void testLocThanhCong() {
        StoreAble mockDB = new InMemoryMockDB();
        LocModel model = new LocModel();
        PresentAble<LocResultDTO> presenter = new LocPresenter(model);
        LocService service = new LocService(mockDB, presenter);

        LocResultDTO dto = service.execute("CUSTOMER", "Running", 41, 3000000L, 4000000L);

        assertEquals(1, dto.actualCount);
        assertTrue(dto.isValid);
        assertEquals("green", model.color);
    }
}

