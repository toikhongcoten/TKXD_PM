package starter;

import starter.shoestore.model.Product;
import starter.shoestore.model.VariantDetailDto;

import java.util.ArrayList;
import java.util.List;

public class XemChiTietResultDTO {
    public boolean actualStore;
    public boolean isAllowed;
    public Product product;
    public List<VariantDetailDto> variants = new ArrayList<>();
    public String errorMessage;
}

