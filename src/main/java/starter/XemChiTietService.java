package starter;

import starter.shoestore.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class XemChiTietService {
    private final StoreAble storeAble;
    private final PresentAble<XemChiTietResultDTO> presenter;

    public XemChiTietService(StoreAble storeAble, PresentAble<XemChiTietResultDTO> presenter) {
        this.storeAble = storeAble;
        this.presenter = presenter;
    }

    public XemChiTietResultDTO execute(String roleStr, String productId) {
        ActorRole role = ActorRole.fromString(roleStr);
        List<Product> allProducts = storeAble.getAllProducts();

        Product product = allProducts.stream()
                .filter(p -> p.getProductId().equalsIgnoreCase(productId))
                .findFirst()
                .orElse(null);

        XemChiTietResultDTO dto = new XemChiTietResultDTO();

        if (product == null) {
            dto.isAllowed = false;
            dto.actualStore = false;
            dto.errorMessage = "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh";
            if (presenter != null) presenter.format(dto);
            return dto;
        }

        if (role != ActorRole.ADMIN && "INACTIVE".equalsIgnoreCase(product.getStatus())) {
            dto.isAllowed = false;
            dto.actualStore = false;
            dto.errorMessage = "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh";
            if (presenter != null) presenter.format(dto);
            return dto;
        }

        dto.isAllowed = true;
        dto.actualStore = true;
        dto.product = product;

        List<ProductVariant> rawVariants = storeAble.getVariantsByProductId(productId);
        dto.variants = rawVariants.stream().map(v -> {
            AvailabilityStatus status;
            if (v.getStock() == 0) {
                status = AvailabilityStatus.SOLD_OUT;
            } else if (v.getStock() <= 5) {
                status = AvailabilityStatus.LOW_STOCK;
            } else {
                status = AvailabilityStatus.AVAILABLE;
            }
            return new VariantDetailDto(
                    v.getSku(),
                    v.getSize(),
                    v.getColor(),
                    v.getPrice(),
                    v.getStock(),
                    status
            );
        }).collect(Collectors.toList());

        if (presenter != null) presenter.format(dto);
        return dto;
    }
}

