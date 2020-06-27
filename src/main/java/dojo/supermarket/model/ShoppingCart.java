package dojo.supermarket.model;

import dojo.supermarket.offer.Offer;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    Map<Product, Double> productQuantities = new HashMap<>();

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }

    public Map<Product, Double> getProductQuantities() {
        return productQuantities;
    }

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

    public int getQuantityAsInt(Product product) {
        return productQuantities.get(product).intValue();
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product : productQuantities().keySet()) {
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);

                Discount discount = offer.getDiscount(catalog, product, this);
                if (discount != null) {
                    receipt.addDiscount(discount);
                }
            }
        }
    }

}
