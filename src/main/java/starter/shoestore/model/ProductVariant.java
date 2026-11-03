package starter.shoestore.model;

public class ProductVariant {
    private String sku;
    private String productId;
    private int size;
    private String color;
    private long price;
    private int stock;

    public ProductVariant() {
    }

    public ProductVariant(String sku, String productId, int size, String color, long price, int stock) {
        this.sku = sku;
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}

