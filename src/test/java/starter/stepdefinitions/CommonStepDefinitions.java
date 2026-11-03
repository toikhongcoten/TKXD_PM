package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import starter.shoestore.model.Product;
import starter.shoestore.model.ProductVariant;

import java.util.List;
import java.util.Map;

/**
 * Các step dùng chung cho cả 3 chức năng:
 * - Background: khởi tạo dữ liệu sản phẩm và biến thể
 * - Given: thiết lập vai trò tác nhân
 */
public class CommonStepDefinitions {

    @Before
    public void resetContext() {
        SharedContext.reset();
    }

    @Given("kho dữ liệu hệ thống đang lưu trữ các sản phẩm giày dép sau:")
    public void kho_du_lieu_luu_tru_san_pham(DataTable table) {
        SharedContext ctx = SharedContext.get();
        ctx.db.clear();
        List<Map<String, String>> rows = table.asMaps();
        for (Map<String, String> row : rows) {
            ctx.db.save(new Product(
                    row.get("productId"),
                    row.get("name"),
                    row.get("category"),
                    row.get("brand"),
                    Long.parseLong(row.get("basePrice")),
                    row.get("status")
            ));
        }
    }

    @And("các sản phẩm trên có danh sách biến thể \\(ProductVariant) chi tiết theo Size và Màu sắc như sau:")
    public void cac_san_pham_co_bien_the(DataTable table) {
        SharedContext ctx = SharedContext.get();
        List<Map<String, String>> rows = table.asMaps();
        for (Map<String, String> row : rows) {
            ctx.db.saveVariant(new ProductVariant(
                    row.get("sku"),
                    row.get("productId"),
                    Integer.parseInt(row.get("size")),
                    row.get("color"),
                    Long.parseLong(row.get("price")),
                    Integer.parseInt(row.get("stock"))
            ));
        }
    }

    @Given("tác nhân đang thực hiện yêu cầu có vai trò là {string}")
    public void tac_nhan_co_vai_tro(String role) {
        SharedContext.get().role = role;
    }
}
