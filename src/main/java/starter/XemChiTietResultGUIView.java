package starter;

import javax.swing.JOptionPane;
import java.awt.GraphicsEnvironment;

/**
 * Humble Object cho chức năng Xem chi tiết sản phẩm.
 * Nhận thông báo từ XemChiTietModel (Subscriber) và hiển thị kết quả lên giao diện.
 * Không chứa bất kỳ logic tính toán hay định dạng nào.
 */
public class XemChiTietResultGUIView implements Subscriber {
    private final XemChiTietModel model;

    public XemChiTietResultGUIView(XemChiTietModel model) {
        this.model = model;
    }

    private void show() {
        String content = "<html><body style='padding: 10px; background-color: " + model.color + "; color: white;'>"
                + "<h3>CHI TIẾT SẢN PHẨM</h3>"
                + "<p><b>Thông báo:</b> " + model.textResult + "</p>"
                + "<p><b>Tên sản phẩm:</b> " + model.productName + "</p>"
                + "<p><b>Tồn kho biến thể:</b> " + model.availabilityStatus + "</p>"
                + "<p><b>Ngày tra cứu:</b> " + model.current_day_vn + "</p>"
                + "</body></html>";
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, content, "Xem chi tiết sản phẩm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[XemChiTietResultGUIView] " + model.textResult + " | Màu: " + model.color);
        }
    }

    @Override
    public void update() {
        show();
    }
}

