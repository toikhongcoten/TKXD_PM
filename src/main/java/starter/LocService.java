package starter;

import starter.shoestore.model.ActorRole;
import starter.shoestore.model.Product;
import starter.shoestore.model.ProductVariant;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LocService {
    private final StoreAble storeAble;
    private final PresentAble<LocResultDTO> presenter;

    public LocService(StoreAble storeAble, PresentAble<LocResultDTO> presenter) {
        this.storeAble = storeAble;
        this.presenter = presenter;
    }

    public LocResultDTO execute(String roleStr, String category, int size, long minPrice, long maxPrice) {
        LocResultDTO dto = new LocResultDTO();

        // Kiểm tra validation nghiệp vụ
        if (minPrice < 0 || maxPrice < 0) {
            dto.isValid = false;
            dto.actualStore = false;
            dto.errorMessage = "Số tiền không được phép mang giá trị âm";
            dto.actualCount = 0;
            dto.products = Collections.emptyList();
            if (presenter != null) presenter.format(dto);
            return dto;
        }

        if (minPrice > maxPrice && maxPrice > 0) {
            dto.isValid = false;
            dto.actualStore = false;
            dto.errorMessage = "Khoảng giá không hợp lệ: Giá tối thiểu lớn hơn giá tối đa";
            dto.actualCount = 0;
            dto.products = Collections.emptyList();
            if (presenter != null) presenter.format(dto);
            return dto;
        }

        if (size > 0 && (size < 20 || size > 55)) {
            dto.isValid = false;
            dto.actualStore = false;
            dto.errorMessage = "Cỡ giày không hợp lệ: Size giày phải nằm trong khoảng 20-55";
            dto.actualCount = 0;
            dto.products = Collections.emptyList();
            if (presenter != null) presenter.format(dto);
            return dto;
        }

        dto.isValid = true;
        dto.actualStore = true;

        ActorRole role = ActorRole.fromString(roleStr);
        List<Product> allProducts = storeAble.getAllProducts();
        List<ProductVariant> allVariants = storeAble.getAllVariants();

        List<Product> matched = allProducts.stream()
                .filter(p -> {
                    if (role != ActorRole.ADMIN && !"ACTIVE".equalsIgnoreCase(p.getStatus())) {
                        return false;
                    }
                    if (category != null && !category.isEmpty() && !"NONE".equalsIgnoreCase(category)
                            && !p.getCategory().equalsIgnoreCase(category)) {
                        return false;
                    }
                    if (minPrice > 0 && p.getBasePrice() < minPrice) return false;
                    if (maxPrice > 0 && p.getBasePrice() > maxPrice) return false;

                    if (size > 0) {
                        boolean hasAvailableSize = allVariants.stream()
                                .anyMatch(v -> v.getProductId().equals(p.getProductId())
                                        && v.getSize() == size
                                        && v.getStock() > 0);
                        if (!hasAvailableSize) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        dto.products = matched;
        dto.actualCount = matched.size();
        if (matched.isEmpty()) {
            dto.businessMessage = "Không tìm thấy sản phẩm nào phù hợp với tiêu chí tìm kiếm";
        }

        if (presenter != null) presenter.format(dto);
        return dto;
    }
}

