package starter;

import starter.shoestore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class LocResultDTO {
    public boolean actualStore;
    public boolean isValid;
    public int actualCount;
    public List<Product> products = new ArrayList<>();
    public String errorMessage;
    public String businessMessage;
}

