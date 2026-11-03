package starter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocPresenter implements PresentAble<LocResultDTO> {
    private final LocModel model;

    public LocPresenter(LocModel model) {
        this.model = model;
    }

    @Override
    public void format(LocResultDTO outputDTO) {
        Date current_day_java = new Date();
        SimpleDateFormat _format = new SimpleDateFormat("dd/MM/yyyy");
        model.current_day_vn = _format.format(current_day_java);

        model.isValid = outputDTO.isValid;
        model.resultCount = outputDTO.actualCount;
        model.errorMessage = outputDTO.errorMessage;

        if (outputDTO.isValid && outputDTO.actualCount > 0) {
            model.color = "green";
            model.textResult = "Lọc thành công: " + outputDTO.actualCount + " sản phẩm";
        } else {
            model.color = "red";
            model.textResult = outputDTO.isValid ? "Không tìm thấy sản phẩm" : outputDTO.errorMessage;
        }
    }
}

