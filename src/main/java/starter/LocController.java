package starter;

public class LocController {
    private final LocService service;
    private final LocModel model;

    public LocController(LocService service, LocModel model) {
        this.service = service;
        this.model = model;
    }

    public void control(String role, String category, int size, long minPrice, long maxPrice) {
        service.execute(role, category, size, minPrice, maxPrice);
        model.notifySubscriber();
    }
}

