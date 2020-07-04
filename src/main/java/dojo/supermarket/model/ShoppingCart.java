package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offer;

import java.util.*;

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
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            applyDiscountAgainstOffers(receipt, offers, catalog, p, quantity);
        }
    }

    private void applyDiscountAgainstOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog, Product p, double quantity) {
        if (isOfferApplicableOnProduct(offers, p)) {
            Offer offer = offers.get(p);
            double unitPrice = catalog.getUnitPrice(p);
            Optional.ofNullable(offer.getDiscount(quantity, unitPrice)).ifPresent(receipt::addDiscount);
        }
    }

    private boolean isOfferApplicableOnProduct(Map<Product, Offer> offers, Product p) {
        return offers.containsKey(p);
    }
}
