package starter;

import javax.swing.JOptionPane;
import java.awt.GraphicsEnvironment;

/**
 * Humble Object cho chức năng Tìm kiếm sản phẩm.
 * Nhận thông báo từ TimKiemModel (Subscriber) và hiển thị kết quả lên giao diện.
 * Không chứa bất kỳ logic tính toán hay định dạng nào.
 */
public class TimKiemResultGUIView implements Subscriber {
    private final TimKiemModel model;

    public TimKiemResultGUIView(TimKiemModel model) {
        this.model = model;
    }

    private void show() {
        String content = "<html><body style='padding: 10px; background-color: " + model.color + "; color: white;'>"
                + "<h3>KẾT QUẢ TÌM KIẾM SẢN PHẨM</h3>"
                + "<p><b>Thông báo:</b> " + model.textResult + "</p>"
                + "<p><b>Số lượng tìm thấy:</b> " + model.resultCount + "</p>"
                + "<p><b>Ngày tra cứu:</b> " + model.current_day_vn + "</p>"
                + "</body></html>";
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, content, "Tìm kiếm sản phẩm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("[TimKiemResultGUIView] " + model.textResult + " | Màu: " + model.color);
        }
    }

    @Override
    public void update() {
        show();
    }
}

