package starter;

import starter.shoestore.model.ActorRole;
import starter.shoestore.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class TimKiemService {
    private final StoreAble storeAble;
    private final PresentAble<TimKiemResultDTO> presenter;

    public TimKiemService(StoreAble storeAble, PresentAble<TimKiemResultDTO> presenter) {
        this.storeAble = storeAble;
        this.presenter = presenter;
    }

    public TimKiemResultDTO execute(String roleStr, String keyword, boolean includeInactive) {
        ActorRole role = ActorRole.fromString(roleStr);
        List<Product> all = storeAble.getAllProducts();

        List<Product> matched = all.stream()
                .filter(p -> {
                    boolean matchKeyword = keyword != null && !keyword.trim().isEmpty() &&
                            (p.getName().toLowerCase().contains(keyword.toLowerCase())
                                    || p.getBrand().toLowerCase().contains(keyword.toLowerCase()));
                    if (!matchKeyword) return false;

                    if (role == ActorRole.ADMIN && includeInactive) {
                        return true;
                    }
                    return "ACTIVE".equalsIgnoreCase(p.getStatus());
                })
                .collect(Collectors.toList());

        TimKiemResultDTO dto = new TimKiemResultDTO();
        dto.products = matched;
        dto.actualCount = matched.size();
        dto.actualStore = true;
        dto.isSuccess = !matched.isEmpty();
        if (matched.isEmpty()) {
            dto.businessMessage = "Không tìm thấy sản phẩm nào phù hợp với tiêu chí tìm kiếm";
        } else {
            dto.businessMessage = "Thành công";
        }

        if (presenter != null) {
            presenter.format(dto);
        }
        return dto;
    }
}

