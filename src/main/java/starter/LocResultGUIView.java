package starter;

import javax.swing.JOptionPane;
import java.awt.GraphicsEnvironment;

/**
 * Humble Object cho chức năng Lọc sản phẩm đa tiêu chí.
 * Nhận thông báo từ LocModel (Subscriber) và hiển thị kết quả lên giao diện.
 * Không chứa bất kỳ logic tính toán hay định dạng nào.
 */
public class LocResultGUIView implements Subscriber {
    private final LocModel model;

    public LocResultGUIView(LocModel model) {
        this.model = model;
    }

    private void show() {
        String content = "<html><body style='padding: 10px; background-color: " + model.color + "; color: white;'>"
                + "<h3>KẾT QUẢ LỌC SẢN PHẨM</h3>"
                + "<p><b>Thông báo:</b> " + model.textResult + "</p>"
                + "<p><b>Số lượng tìm thấy:</b> " + model.resultCount + "</p>"
                + "<p><b>Tính hợp lệ:</b> " + model.isValid + "</p>"
                + "<p><b>Ngày lọc:</b> " + model.current_day_vn + "</p>"
                + "</body></html>";
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, content, "Lọc sản phẩm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[LocResultGUIView] " + model.textResult + " | Màu: " + model.color);
        }
    }

    @Override
    public void update() {
        show();
    }
}

