package starter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimKiemPresenter implements PresentAble<TimKiemResultDTO> {
    private final TimKiemModel model;

    public TimKiemPresenter(TimKiemModel model) {
        this.model = model;
    }

    @Override
    public void format(TimKiemResultDTO outputDTO) {
        Date current_day_java = new Date();
        SimpleDateFormat _format = new SimpleDateFormat("dd/MM/yyyy");
        model.current_day_vn = _format.format(current_day_java);

        model.resultCount = outputDTO.actualCount;
        model.businessMessage = outputDTO.businessMessage;

        if (outputDTO.isSuccess && outputDTO.actualCount > 0) {
            model.color = "green";
            model.textResult = "Tìm thấy " + outputDTO.actualCount + " sản phẩm";
            model.productStatus = outputDTO.products.get(0).getStatus();
        } else {
            model.color = "red";
            model.textResult = "Không tìm thấy sản phẩm nào";
            model.productStatus = "NONE";
        }
    }
}

