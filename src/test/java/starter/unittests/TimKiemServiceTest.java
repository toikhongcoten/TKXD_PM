package starter.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import starter.InMemoryMockDB;
import starter.PresentAble;
import starter.StoreAble;
import starter.TimKiemModel;
import starter.TimKiemPresenter;
import starter.TimKiemResultDTO;
import starter.TimKiemService;

public class TimKiemServiceTest {

    @Test
    @DisplayName("Khi thực thi tìm kiếm sản phẩm đang mở bán với vai trò GUEST")
    void testTimKiemGuest() {
        StoreAble mockDB = new InMemoryMockDB();
        TimKiemModel model = new TimKiemModel();
        PresentAble<TimKiemResultDTO> presenter = new TimKiemPresenter(model);
        TimKiemService service = new TimKiemService(mockDB, presenter);

        TimKiemResultDTO dto = service.execute("GUEST", "Nike", false);

        assertEquals(1, dto.actualCount);
        assertTrue(dto.actualStore);
        assertEquals("green", model.color);
        assertEquals("ACTIVE", model.productStatus);
    }
}

