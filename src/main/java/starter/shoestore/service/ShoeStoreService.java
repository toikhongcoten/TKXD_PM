package starter.shoestore.service;

import starter.shoestore.exception.BusinessException;
import starter.shoestore.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ShoeStoreService {

    private final Map<String, Product> productStore = new LinkedHashMap<>();
    private final Map<String, List<ProductVariant>> variantStore = new LinkedHashMap<>();

    public void clearData() {
        productStore.clear();
        variantStore.clear();
    }

    public void addProduct(Product product) {
        productStore.put(product.getProductId(), product);
    }

    public void addVariant(ProductVariant variant) {
        variantStore.computeIfAbsent(variant.getProductId(), k -> new ArrayList<>()).add(variant);
    }

    public List<Product> searchProducts(ActorRole role, String keyword, boolean includeInactive) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase();

        return productStore.values().stream()
                .filter(p -> {
                    boolean matchesKeyword = p.getName().toLowerCase().contains(query)
                            || p.getBrand().toLowerCase().contains(query)
                            || p.getCategory().toLowerCase().contains(query);
                    if (!matchesKeyword) {
                        return false;
                    }
                    if (role == ActorRole.ADMIN && includeInactive) {
                        return true;
                    }
                    return "ACTIVE".equalsIgnoreCase(p.getStatus());
                })
                .collect(Collectors.toList());
    }

    public List<Product> filterProducts(ActorRole role, String category, Integer size, Long minPrice, Long maxPrice) {
        // Validate price range
        if (minPrice != null && minPrice < 0) {
            throw new BusinessException("Số tiền không được phép mang giá trị âm");
        }
        if (maxPrice != null && maxPrice < 0) {
            throw new BusinessException("Số tiền không được phép mang giá trị âm");
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new BusinessException("Khoảng giá không hợp lệ: Giá tối thiểu lớn hơn giá tối đa");
        }

        // Validate shoe size
        if (size != null && (size < 20 || size > 55)) {
            throw new BusinessException("Cỡ giày không hợp lệ: Size giày phải nằm trong khoảng 20-55");
        }

        return productStore.values().stream()
                .filter(p -> {
                    // Role check
                    if (role != ActorRole.ADMIN && !"ACTIVE".equalsIgnoreCase(p.getStatus())) {
                        return false;
                    }

                    // Category filter
                    if (category != null && !category.isEmpty() && !p.getCategory().equalsIgnoreCase(category)) {
                        return false;
                    }

                    List<ProductVariant> variants = variantStore.getOrDefault(p.getProductId(), Collections.emptyList());

                    // Size filter: must have variant with matching size and stock > 0
                    if (size != null) {
                        List<ProductVariant> availableSizeVariants = variants.stream()
                                .filter(v -> v.getSize() == size && v.getStock() > 0)
                                .collect(Collectors.toList());

                        if (availableSizeVariants.isEmpty()) {
                            return false;
                        }

                        // Check price on matching variants or product base price
                        if (minPrice != null || maxPrice != null) {
                            boolean priceMatches = availableSizeVariants.stream().anyMatch(v -> {
                                long price = v.getPrice();
                                if (minPrice != null && price < minPrice) return false;
                                if (maxPrice != null && price > maxPrice) return false;
                                return true;
                            });
                            if (!priceMatches) {
                                return false;
                            }
                        }
                    } else if (minPrice != null || maxPrice != null) {
                        long price = p.getBasePrice();
                        if (minPrice != null && price < minPrice) return false;
                        if (maxPrice != null && price > maxPrice) return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetail(ActorRole role, String productId) {
        Product product = productStore.get(productId);
        if (product == null) {
            throw new BusinessException("Sản phẩm không tồn tại hoặc đã ngừng kinh doanh");
        }

        if ("INACTIVE".equalsIgnoreCase(product.getStatus()) && role != ActorRole.ADMIN) {
            throw new BusinessException("Sản phẩm không tồn tại hoặc đã ngừng kinh doanh");
        }

        ProductDetailDto dto = new ProductDetailDto(
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getBasePrice(),
                product.getStatus()
        );

        List<ProductVariant> variants = variantStore.getOrDefault(productId, Collections.emptyList());
        List<VariantDetailDto> variantDtos = variants.stream()
                .map(v -> new VariantDetailDto(
                        v.getSku(),
                        v.getSize(),
                        v.getColor(),
                        v.getPrice(),
                        v.getStock(),
                        AvailabilityStatus.fromStock(v.getStock())
                ))
                .collect(Collectors.toList());

        dto.setVariants(variantDtos);
        return dto;
    }
}
