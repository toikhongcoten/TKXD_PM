package starter.stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.*;
import starter.shoestore.model.VariantDetailDto;

import java.util.List;
import java.util.Map;

public class XemChiTietStepDefinitions {

    private final XemChiTietModel model = new XemChiTietModel();
    private final PresentAble<XemChiTietResultDTO> presenter = new XemChiTietPresenter(model);

    @When("tác nhân yêu cầu xem thông tin chi tiết của sản phẩm có mã {string}")
    public void tac_nhan_yeu_cau_xem_chi_tiet(String productId) {
        SharedContext ctx = SharedContext.get();
        XemChiTietService service = new XemChiTietService(ctx.db, presenter);
        ctx.xemChiTietResult = service.execute(ctx.role, productId);
    }

    @Then("hệ thống trả về thông tin sản phẩm gốc {string} thuộc danh mục {string}")
    public void he_thong_tra_ve_thong_tin_san_pham(String expectedName, String expectedCategory) {
        SharedContext ctx = SharedContext.get();
        assertTrue(ctx.xemChiTietResult.isAllowed);
        assertNotNull(ctx.xemChiTietResult.product);
        assertEquals(expectedName, ctx.xemChiTietResult.product.getName());
        assertEquals(expectedCategory, ctx.xemChiTietResult.product.getCategory());
    }

    @And("danh sách biến thể của sản phẩm {string} được phân loại trạng thái tồn kho chính xác như sau:")
    public void danh_sach_bien_the_phan_loai_ton_kho(String productId, DataTable table) {
        SharedContext ctx = SharedContext.get();
        List<Map<String, String>> rows = table.asMaps();
        assertEquals(rows.size(), ctx.xemChiTietResult.variants.size());
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> expected = rows.get(i);
            VariantDetailDto actual = ctx.xemChiTietResult.variants.get(i);
            assertEquals(expected.get("sku"), actual.getSku());
            assertEquals(Integer.parseInt(expected.get("size")), actual.getSize());
            assertEquals(expected.get("color"), actual.getColor());
            assertEquals(Long.parseLong(expected.get("price")), actual.getPrice());
            assertEquals(Integer.parseInt(expected.get("stock")), actual.getStock());
            assertEquals(expected.get("availabilityStatus"), actual.getAvailabilityStatus().name());
        }
    }

    @And("trạng thái kinh doanh của sản phẩm được ghi nhận là {string}")
    public void trang_thai_kinh_doanh_duoc_ghi_nhan(String expectedStatus) {
        SharedContext ctx = SharedContext.get();
        assertNotNull(ctx.xemChiTietResult.product);
        assertEquals(expectedStatus, ctx.xemChiTietResult.product.getStatus());
    }

    @Then("hệ thống từ chối cung cấp thông tin và trả về lỗi nghiệp vụ {string}")
    public void he_thong_tu_choi_cung_cap_thong_tin(String expectedError) {
        SharedContext ctx = SharedContext.get();
        assertFalse(ctx.xemChiTietResult.isAllowed);
        assertEquals(expectedError, ctx.xemChiTietResult.errorMessage);
    }
}
