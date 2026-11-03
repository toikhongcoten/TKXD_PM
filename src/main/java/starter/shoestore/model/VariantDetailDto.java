package starter.shoestore.model;

public class VariantDetailDto {
    private String sku;
    private int size;
    private String color;
    private long price;
    private int stock;
    private AvailabilityStatus availabilityStatus;

    public VariantDetailDto() {
    }

    public VariantDetailDto(String sku, int size, String color, long price, int stock, AvailabilityStatus availabilityStatus) {
        this.sku = sku;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.availabilityStatus = availabilityStatus;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}

