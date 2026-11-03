package starter.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import starter.InMemoryMockDB;
import starter.PresentAble;
import starter.StoreAble;
import starter.XemChiTietModel;
import starter.XemChiTietPresenter;
import starter.XemChiTietResultDTO;
import starter.XemChiTietService;

public class XemChiTietServiceTest {

    @Test
    @DisplayName("Khi xem chi tiết sản phẩm thành công với vai trò CUSTOMER")
    void testXemChiTietThanhCong() {
        StoreAble mockDB = new InMemoryMockDB();
        XemChiTietModel model = new XemChiTietModel();
        PresentAble<XemChiTietResultDTO> presenter = new XemChiTietPresenter(model);
        XemChiTietService service = new XemChiTietService(mockDB, presenter);

        XemChiTietResultDTO dto = service.execute("CUSTOMER", "SP01");

        assertTrue(dto.isAllowed);
        assertEquals("Nike Air Zoom Pegasus 40", dto.product.getName());
        assertEquals("green", model.color);
        assertEquals("AVAILABLE", model.availabilityStatus);
    }
}

