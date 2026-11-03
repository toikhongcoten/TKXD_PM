package starter.stepdefinitions;

import starter.InMemoryMockDB;
import starter.LocResultDTO;
import starter.TimKiemResultDTO;
import starter.XemChiTietResultDTO;

/**
 * Thread-safe shared state giữa các step definition class.
 */
public class SharedContext {
    private static final ThreadLocal<SharedContext> CONTEXT = ThreadLocal.withInitial(SharedContext::new);

    public final InMemoryMockDB db = new InMemoryMockDB();
    public String role;
    public TimKiemResultDTO timKiemResult;
    public LocResultDTO locResult;
    public XemChiTietResultDTO xemChiTietResult;

    public static SharedContext get() {
        return CONTEXT.get();
    }

    public static void reset() {
        SharedContext ctx = new SharedContext();
        ctx.db.clear();
        CONTEXT.set(ctx);
    }
}
