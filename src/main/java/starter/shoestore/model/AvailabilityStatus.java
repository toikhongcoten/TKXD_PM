package starter.shoestore.model;

public enum AvailabilityStatus {
    AVAILABLE,
    LOW_STOCK,
    SOLD_OUT;

    public static AvailabilityStatus fromStock(int stock) {
        if (stock > 5) {
            return AVAILABLE;
        } else if (stock >= 1) {
            return LOW_STOCK;
        } else {
            return SOLD_OUT;
        }
    }
}

