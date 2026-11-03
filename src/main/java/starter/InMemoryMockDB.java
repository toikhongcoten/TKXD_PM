package starter;

import starter.shoestore.model.Product;
import starter.shoestore.model.ProductVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryMockDB implements StoreAble {
    private final List<Product> productDB = new ArrayList<>();
    private final List<ProductVariant> variantDB = new ArrayList<>();

    public InMemoryMockDB() {
        initSampleData();
    }

    public void initSampleData() {
        clear();
        save(new Product("SP01", "Nike Air Zoom Pegasus 40", "Running", "Nike", 3200000L, "ACTIVE"));
        save(new Product("SP02", "Adidas Ultraboost Light", "Running", "Adidas", 4500000L, "ACTIVE"));
        save(new Product("SP03", "Biti's Hunter X Dune", "Sneaker", "Bitis", 1100000L, "ACTIVE"));
        save(new Product("SP04", "Converse Chuck 70 Classic", "Sneaker", "Converse", 1800000L, "INACTIVE"));
        save(new Product("SP05", "Puma Velocity Nitro 2", "Running", "Puma", 2800000L, "ACTIVE"));

        saveVariant(new ProductVariant("SP01-40-BLK", "SP01", 40, "Black", 3200000L, 10));
        saveVariant(new ProductVariant("SP01-41-BLK", "SP01", 41, "Black", 3200000L, 3));
        saveVariant(new ProductVariant("SP01-42-WHT", "SP01", 42, "White", 3300000L, 0));
        saveVariant(new ProductVariant("SP02-41-WHT", "SP02", 41, "White", 4500000L, 8));
        saveVariant(new ProductVariant("SP02-42-WHT", "SP02", 42, "White", 4500000L, 2));
        saveVariant(new ProductVariant("SP03-39-GRN", "SP03", 39, "Green", 1100000L, 15));
        saveVariant(new ProductVariant("SP03-41-GRN", "SP03", 41, "Green", 1100000L, 0));
        saveVariant(new ProductVariant("SP04-41-BLK", "SP04", 41, "Black", 1800000L, 5));
        saveVariant(new ProductVariant("SP05-40-ORG", "SP05", 40, "Orange", 2800000L, 0));
    }

    @Override
    public boolean save(Product product) {
        return productDB.add(product);
    }

    @Override
    public boolean saveVariant(ProductVariant variant) {
        return variantDB.add(variant);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productDB);
    }

    @Override
    public List<ProductVariant> getAllVariants() {
        return new ArrayList<>(variantDB);
    }

    @Override
    public List<ProductVariant> getVariantsByProductId(String productId) {
        return variantDB.stream()
                .filter(v -> v.getProductId().equalsIgnoreCase(productId))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        productDB.clear();
        variantDB.clear();
    }
}

