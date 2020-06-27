package dojo.supermarket.model;

import dojo.supermarket.discounts.Discount;
import dojo.supermarket.discounts.DiscountFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        productQuantities()
                .keySet()
                .stream()
                .filter(offers::containsKey)
                .forEach(product -> {
                    Offer offer = offers.get(product);
                    double unitPrice = catalog.getUnitPrice(product);

                    Discount discount = DiscountFactory.getInstance(offer, product, productQuantities.get(product), unitPrice);
                    receipt.addDiscount(discount);
                });
    }
}
