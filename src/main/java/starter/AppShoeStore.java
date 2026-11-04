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
        TimKiemResultGUIView timKiemView = new TimKiemResultGUIView(timKiemModel);
        timKiemModel.addSubscriber(timKiemView);

        // 2. Chức năng Lọc
        LocModel locModel = new LocModel();
        PresentAble<LocResultDTO> locPresenter = new LocPresenter(locModel);
        LocService locService = new LocService(mockDB, locPresenter);
        LocController locController = new LocController(locService, locModel);
        LocResultGUIView locView = new LocResultGUIView(locModel);
        locModel.addSubscriber(locView);

        // 3. Chức năng Xem chi tiết
        XemChiTietModel xemChiTietModel = new XemChiTietModel();
        PresentAble<XemChiTietResultDTO> xemChiTietPresenter = new XemChiTietPresenter(xemChiTietModel);
        XemChiTietService xemChiTietService = new XemChiTietService(mockDB, xemChiTietPresenter);
        XemChiTietController xemChiTietController = new XemChiTietController(xemChiTietService, xemChiTietModel);
        XemChiTietResultGUIView xemChiTietView = new XemChiTietResultGUIView(xemChiTietModel);
        xemChiTietModel.addSubscriber(xemChiTietView);

        // Chạy thử nghiệm Controller
        System.out.println("=== CHẠY THỬ NGHIỆM HỆ THỐNG GIÀY DÉP (THEO FORM TKXPM - HUMBLE OBJECT) ===");
        timKiemController.control("GUEST", "Nike", false);
        locController.control("CUSTOMER", "Running", 41, 3000000L, 4000000L);
        xemChiTietController.control("CUSTOMER", "SP01");
    }
}
