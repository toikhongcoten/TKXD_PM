package starter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XemChiTietPresenter implements PresentAble<XemChiTietResultDTO> {
    private final XemChiTietModel model;

    public XemChiTietPresenter(XemChiTietModel model) {
        this.model = model;
    }

    @Override
    public void format(XemChiTietResultDTO outputDTO) {
        Date current_day_java = new Date();
        SimpleDateFormat _format = new SimpleDateFormat("dd/MM/yyyy");
        model.current_day_vn = _format.format(current_day_java);

        model.isAllowed = outputDTO.isAllowed;
        model.errorMessage = outputDTO.errorMessage;

        if (outputDTO.isAllowed && outputDTO.product != null) {
            model.color = "green";
            model.productName = outputDTO.product.getName();
            model.productStatus = outputDTO.product.getStatus();
            model.textResult = "Xem chi tiết: " + outputDTO.product.getName();
            if (outputDTO.variants != null && !outputDTO.variants.isEmpty()) {
                model.availabilityStatus = outputDTO.variants.get(0).getAvailabilityStatus().name();
            } else {
                model.availabilityStatus = "NONE";
            }
        } else {
            model.color = "red";
            model.productName = "NONE";
            model.productStatus = "NONE";
            model.availabilityStatus = "NONE";
            model.textResult = outputDTO.errorMessage;
        }
    }
}

