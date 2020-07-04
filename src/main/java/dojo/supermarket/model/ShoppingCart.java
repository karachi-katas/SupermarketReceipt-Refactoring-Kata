package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        Double oldQuantity = productQuantities.getOrDefault(product, 0.0);
        productQuantities.put(product, oldQuantity + quantity);
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities.keySet()) {
            applyDiscount(receipt, offers, catalog, product);
        }
    }

    private void applyDiscount(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog, Product product) {
        double quantity = productQuantities.get(product);
        if (offers.containsKey(product)) {
            Offer offer = offers.get(product);
            offer.applyDiscount(receipt, catalog, quantity);
        }
    }

    void handleProducts(Receipt receipt, SupermarketCatalog catalog) {
        for (ProductQuantity pq : items) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = catalog.getUnitPrice(p);
            receipt.addProduct(p, quantity, unitPrice);
        }
    }
}
