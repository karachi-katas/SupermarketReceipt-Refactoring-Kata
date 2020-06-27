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
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                Discount discount = null;
                discount = calculateTwoForAmountDiscount(p, quantity, offer, unitPrice);
                discount = calculateThreeForTwoDiscount(p, quantity, offer, unitPrice, discount);
                discount = calculateTenPercentDiscount(p, quantity, offer, unitPrice, discount);
                discount = calculatefiveForAmountDiscount(p, quantity, offer, unitPrice, discount);
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private Discount calculateTwoForAmountDiscount(Product p, double quantity, Offer offer, double unitPrice) {

        int quantityAsInt = (int) quantity;
        if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
            int x = 2;
            double total = offer.argument * (quantityAsInt / x) + quantityAsInt % 2 * unitPrice;
            double discountN = unitPrice * quantity - total;
            return new Discount(p, "2 for " + offer.argument, -discountN);

        }
        return null;
    }

    private Discount calculateThreeForTwoDiscount(Product p, double quantity, Offer offer,
                                                  double unitPrice, Discount discount) {

        int quantityAsInt = (int) quantity;
        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            int numberOfXs = quantityAsInt / 3;
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(p, "3 for 2", -discountAmount);
        }
        return discount;
    }

    private Discount calculateTenPercentDiscount(Product p, double quantity, Offer offer,
                                                 double unitPrice, Discount discount) {
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        }
        return discount;
    }

    private Discount calculatefiveForAmountDiscount(Product p, double quantity, Offer offer,
                                                    double unitPrice, Discount discount) {

        int quantityAsInt = (int) quantity;
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            int numberOfXs = quantityAsInt / 5;
            double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(p, "5 for " + offer.argument, -discountTotal);
        }
        return discount;
    }
}
