package starter;

public class AppShoeStore {
    public static void main(String[] args) {
        // Khởi tạo Database giả lập trong bộ nhớ RAM
        StoreAble mockDB = new InMemoryMockDB();

        // 1. Chức năng Tìm kiếm
        TimKiemModel timKiemModel = new TimKiemModel();
        PresentAble<TimKiemResultDTO> timKiemPresenter = new TimKiemPresenter(timKiemModel);
        TimKiemService timKiemService = new TimKiemService(mockDB, timKiemPresenter);
        TimKiemController timKiemController = new TimKiemController(timKiemService, timKiemModel);

        // Đăng ký Subscriber (Observer Pattern)
        timKiemModel.addSubscriber(new Subscriber() {
            @Override
            public void update() {
                System.out.println("[TimKiemSubscriber] Kết quả: " + timKiemModel.textResult + " | Màu: " + timKiemModel.color);
            }
        });

        // 2. Chức năng Lọc
        LocModel locModel = new LocModel();
        PresentAble<LocResultDTO> locPresenter = new LocPresenter(locModel);
        LocService locService = new LocService(mockDB, locPresenter);
        LocController locController = new LocController(locService, locModel);

        locModel.addSubscriber(new Subscriber() {
            @Override
            public void update() {
                System.out.println("[LocSubscriber] Kết quả: " + locModel.textResult + " | Màu: " + locModel.color);
            }
        });

        // 3. Chức năng Xem chi tiết
        XemChiTietModel xemChiTietModel = new XemChiTietModel();
        PresentAble<XemChiTietResultDTO> xemChiTietPresenter = new XemChiTietPresenter(xemChiTietModel);
        XemChiTietService xemChiTietService = new XemChiTietService(mockDB, xemChiTietPresenter);
        XemChiTietController xemChiTietController = new XemChiTietController(xemChiTietService, xemChiTietModel);

        xemChiTietModel.addSubscriber(new Subscriber() {
            @Override
            public void update() {
                System.out.println("[XemChiTietSubscriber] Kết quả: " + xemChiTietModel.textResult + " | Màu: " + xemChiTietModel.color);
            }
        });

        // Chạy thử nghiệm Controller
        System.out.println("=== CHẠY THỬ NGHIỆM HỆ THỐNG GIÀY DÉP (THEO FORM TKXPM) ===");
        timKiemController.control("GUEST", "Nike", false);
        locController.control("CUSTOMER", "Running", 41, 3000000L, 4000000L);
        xemChiTietController.control("CUSTOMER", "SP01");
    }
}

