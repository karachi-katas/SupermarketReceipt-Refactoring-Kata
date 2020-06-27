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

    private double getQuantity(Product product) {
        return productQuantities.get(product);
    }

    private int getQuantityAsInt(Product product) {
        return productQuantities.get(product).intValue();
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities().keySet()) {
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);
                Discount discount = null;

                if (offer.offerType == SpecialOfferType.BundledDiscount) {
                    discount = offer.getDiscount(catalog, product, this);
                }

                if (offer.offerType == SpecialOfferType.TwoForAmount) {
                    discount = getDiscount(catalog, product, offer);

                }

                if (offer.offerType == SpecialOfferType.ThreeForTwo && getQuantityAsInt(product) > 2) {
                    int numberOfXs = getQuantityAsInt(product) / 3;
                    double discountAmount = getQuantity(product) * catalog.getUnitPrice(product) - ((numberOfXs * 2 * catalog.getUnitPrice(product)) + getQuantityAsInt(product) % 3 * catalog.getUnitPrice(product));
                    discount = new Discount(product, "3 for 2", -discountAmount);
                }
                if (offer.offerType == SpecialOfferType.PercentDiscount) {
                    discount = new Discount(product, offer.discountPercentageOrAmount + "% off", -getQuantity(product) * catalog.getUnitPrice(product) * offer.discountPercentageOrAmount / 100.0);
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount && getQuantityAsInt(product) >= 5) {
                    int numberOfXs = getQuantityAsInt(product) / 5;
                    double discountTotal = catalog.getUnitPrice(product) * getQuantity(product) - (offer.discountPercentageOrAmount * numberOfXs + getQuantityAsInt(product) % 5 * catalog.getUnitPrice(product));
                    discount = new Discount(product, 5 + " for " + offer.discountPercentageOrAmount, -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private Discount getDiscount(SupermarketCatalog catalog, Product product, Offer offer) {
        if (getQuantityAsInt(product) >= 2) {
            double total = offer.discountPercentageOrAmount * (getQuantityAsInt(product) / 2) + getQuantityAsInt(product) % 2 * catalog.getUnitPrice(product);
            double discountN = catalog.getUnitPrice(product) * getQuantity(product) - total;
            return new Discount(product, "2 for " + offer.discountPercentageOrAmount, -discountN);
        }
        return null;
    }

}
