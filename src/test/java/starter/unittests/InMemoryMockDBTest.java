package starter.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import starter.InMemoryMockDB;

public class InMemoryMockDBTest {

    @Test
    @DisplayName("Kiểm tra khởi tạo dữ liệu mẫu trong InMemoryMockDB")
    void testInitSampleData() {
        InMemoryMockDB mockDB = new InMemoryMockDB();
        assertFalse(mockDB.getAllProducts().isEmpty());
        assertEquals(5, mockDB.getAllProducts().size());
        assertEquals(9, mockDB.getAllVariants().size());
    }
}

