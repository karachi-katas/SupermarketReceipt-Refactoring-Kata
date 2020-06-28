package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class FakeCatalog implements SupermarketCatalog {
    private Map<Product, Double> prices = new HashMap<>();

    @Override
    public void addProduct(Product product, double price) {
        this.prices.put(product, price);
    }

    @Override
    public double getUnitPrice(Product product) {
        return this.prices.get(product);
    }
}
