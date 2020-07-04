package dojo.supermarket.model;

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
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                applyDiscount(receipt, catalog, quantity, offer);
            }

        }
    }

    private void applyDiscount(Receipt receipt, SupermarketCatalog catalog,
            double quantity, Offer offer) {
        Product product = offer.getProduct();
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = offer.getDiscount(product, quantity, unitPrice);
        }
        else {
            if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                int x = 3;
                int numberOfXs = quantityAsInt / x;
                double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                discount = new Discount(product, "3 for 2", -discountAmount);
            }
            if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                int x = 2;
                int numberOfXs = quantityAsInt / x;
                double total = offer.argument * numberOfXs + quantityAsInt % 2 * unitPrice;
                double discountN = unitPrice * quantity - total;
                discount = new Discount(product, "2 for " + offer.argument, -discountN);
            }
            if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                int x = 5;
                int numberOfXs = quantityAsInt / x;
                double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                discount = new Discount(product, x + " for " + offer.argument, -discountTotal);
            }
        }
        if (discount != null)
            receipt.addDiscount(discount);
    }
}
