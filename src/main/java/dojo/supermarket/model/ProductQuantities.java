package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductQuantities {

    Map<Product, Double> productQuantities = new HashMap<>();

    public void addItemQuantity(Product product, double quantity) {
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    public double getQuantity(Product product) {
        return productQuantities.get(product);
    }

    public Set<Product> getProducts() {
        return productQuantities.keySet();
    }

    public List<ProductQuantity> getList() {
        return productQuantities.entrySet().stream()
            .map(mapEntry ->  new ProductQuantity(mapEntry.getKey(), mapEntry.getValue()))
            .collect(Collectors.toList());
    }

}
