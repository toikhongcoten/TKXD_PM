package starter;

import starter.shoestore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class TimKiemResultDTO {
    public boolean actualStore;
    public int actualCount;
    public List<Product> products = new ArrayList<>();
    public String businessMessage;
    public boolean isSuccess;
}

