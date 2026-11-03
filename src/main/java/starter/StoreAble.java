package starter;

import starter.shoestore.model.Product;
import starter.shoestore.model.ProductVariant;

import java.util.List;

public interface StoreAble {
    boolean save(Product product);
    boolean saveVariant(ProductVariant variant);
    List<Product> getAllProducts();
    List<ProductVariant> getAllVariants();
    List<ProductVariant> getVariantsByProductId(String productId);
    void clear();
}

