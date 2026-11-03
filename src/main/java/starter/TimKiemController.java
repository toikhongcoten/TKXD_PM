package starter;

public class TimKiemController {
    private final TimKiemService service;
    private final TimKiemModel model;

    public TimKiemController(TimKiemService service, TimKiemModel model) {
        this.service = service;
        this.model = model;
    }

    public void control(String role, String keyword, boolean includeInactive) {
        service.execute(role, keyword, includeInactive);
        model.notifySubscriber();
    }
}

