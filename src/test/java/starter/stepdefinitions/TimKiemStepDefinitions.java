package starter.stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.*;
import starter.shoestore.model.Product;

import java.util.List;
import java.util.Map;

public class TimKiemStepDefinitions {

    private final TimKiemModel model = new TimKiemModel();
    private final PresentAble<TimKiemResultDTO> presenter = new TimKiemPresenter(model);

    @When("tác nhân thực hiện tìm kiếm sản phẩm với từ khóa {string}")
    public void tac_nhan_tim_kiem_tu_khoa(String keyword) {
        SharedContext ctx = SharedContext.get();
        TimKiemService service = new TimKiemService(ctx.db, presenter);
        ctx.timKiemResult = service.execute(ctx.role, keyword, false);
    }

    @When("tác nhân thực hiện tra cứu sản phẩm với từ khóa {string} cùng tùy chọn bao gồm sản phẩm ngừng kinh doanh")
    public void tac_nhan_tra_cuu_bao_gom_ngung_kinh_doanh(String keyword) {
        SharedContext ctx = SharedContext.get();
        TimKiemService service = new TimKiemService(ctx.db, presenter);
        ctx.timKiemResult = service.execute(ctx.role, keyword, true);
    }

    @Then("hệ thống trả về danh sách gồm {int} sản phẩm khớp yêu cầu:")
    public void he_thong_tra_ve_danh_sach(int expectedCount, DataTable table) {
        SharedContext ctx = SharedContext.get();
        List<Product> products;
        int actualCount;
        if (ctx.timKiemResult != null) {
            products = ctx.timKiemResult.products;
            actualCount = ctx.timKiemResult.actualCount;
        } else {
            products = ctx.locResult.products;
            actualCount = ctx.locResult.actualCount;
        }
        assertEquals(expectedCount, actualCount);
        List<Map<String, String>> rows = table.asMaps();
        assertEquals(rows.size(), products.size());
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> expected = rows.get(i);
            Product actual = products.get(i);
            assertEquals(expected.get("productId"), actual.getProductId());
            assertEquals(expected.get("name"), actual.getName());
            assertEquals(expected.get("status"), actual.getStatus());
        }
    }

    @Then("hệ thống trả về danh sách sản phẩm rỗng")
    public void he_thong_tra_ve_danh_sach_rong() {
        SharedContext ctx = SharedContext.get();
        if (ctx.timKiemResult != null) {
            assertEquals(0, ctx.timKiemResult.actualCount);
            assertTrue(ctx.timKiemResult.products.isEmpty());
        } else {
            assertEquals(0, ctx.locResult.actualCount);
            assertTrue(ctx.locResult.products.isEmpty());
        }
    }

    @And("hệ thống ghi nhận thông điệp nghiệp vụ {string}")
    public void he_thong_ghi_nhan_thong_diep(String expectedMessage) {
        SharedContext ctx = SharedContext.get();
        if (ctx.timKiemResult != null) {
            assertEquals(expectedMessage, ctx.timKiemResult.businessMessage);
        } else {
            assertEquals(expectedMessage, ctx.locResult.businessMessage);
        }
    }
}
