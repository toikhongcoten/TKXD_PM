package starter;

public class XemChiTietController {
    private final XemChiTietService service;
    private final XemChiTietModel model;

    public XemChiTietController(XemChiTietService service, XemChiTietModel model) {
        this.service = service;
        this.model = model;
    }

    public void control(String role, String productId) {
        service.execute(role, productId);
        model.notifySubscriber();
    }
}

