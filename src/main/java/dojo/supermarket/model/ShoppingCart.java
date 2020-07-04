package dojo.supermarket.model;

import dojo.supermarket.discount.Discount;
import dojo.supermarket.discount.DiscountCalculator;
import dojo.supermarket.type.SpecialOfferType;

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

    public Discount getCartDiscount(Map<Product, Offer> offers, SupermarketCatalog catalog) {
        Discount discount = null;
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {

                Offer offer = offers.get(p);

                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;

                int quantityAdjustment = offer.getQuantityAdjustment();

                int numberOfXs = quantityAsInt / quantityAdjustment;

                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {

                    DiscountCalculator discountThreeForTwo = new ThreeForTwo();
                    return discountThreeForTwo.calculateDiscount(offer,catalog,p,quantity);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    return new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                    return new Discount(p, quantityAdjustment + " for " + offer.argument, -discountTotal);
                }
                if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                    double total = offer.argument * (quantityAsInt / quantityAdjustment) + quantityAsInt % 2 * unitPrice;
                    double discountN = unitPrice * quantity - total;
                    return new Discount(p, "2 for " + offer.argument, -discountN);
                }

            }
        }
        return discount;

    }
}

