package starter.stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.*;

public class LocStepDefinitions {

    private final LocModel model = new LocModel();
    private final PresentAble<LocResultDTO> presenter = new LocPresenter(model);

    @When("tác nhân lọc sản phẩm theo danh mục {string}, cỡ giày {int}, và khoảng giá từ {long} đến {long}")
    public void tac_nhan_loc_da_tieu_chi(String category, int size, long minPrice, long maxPrice) {
        SharedContext ctx = SharedContext.get();
        LocService service = new LocService(ctx.db, presenter);
        ctx.locResult = service.execute(ctx.role, category, size, minPrice, maxPrice);
    }

    @When("tác nhân lọc sản phẩm với mức giá tối thiểu là {long} và mức giá tối đa là {long}")
    public void tac_nhan_loc_khoang_gia(long minPrice, long maxPrice) {
        SharedContext ctx = SharedContext.get();
        LocService service = new LocService(ctx.db, presenter);
        ctx.locResult = service.execute(ctx.role, null, 0, minPrice, maxPrice);
    }

    @When("tác nhân lọc sản phẩm với cỡ giày yêu cầu là {int}")
    public void tac_nhan_loc_co_giay(int invalidSize) {
        SharedContext ctx = SharedContext.get();
        LocService service = new LocService(ctx.db, presenter);
        ctx.locResult = service.execute(ctx.role, null, invalidSize, 0, 0);
    }

    @Then("hệ thống từ chối thực thi tìm kiếm và trả về lỗi nghiệp vụ {string}")
    public void he_thong_tu_choi_thuc_thi(String expectedError) {
        SharedContext ctx = SharedContext.get();
        assertFalse(ctx.locResult.isValid);
        assertEquals(expectedError, ctx.locResult.errorMessage);
    }
}
