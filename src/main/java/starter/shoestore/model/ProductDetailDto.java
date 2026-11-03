package starter.shoestore.model;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailDto {
    private String productId;
    private String name;
    private String category;
    private String brand;
    private long basePrice;
    private String status;
    private List<VariantDetailDto> variants = new ArrayList<>();

    public ProductDetailDto() {
    }

    public ProductDetailDto(String productId, String name, String category, String brand, long basePrice, String status) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.basePrice = basePrice;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(long basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VariantDetailDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDetailDto> variants) {
        this.variants = variants;
    }
}

