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
        double previousQuantity = isExistingProduct(product)? productQuantities.get(product): 0;

        productQuantities.put(product, previousQuantity + quantity);
    }

    private boolean isExistingProduct(Product product) {
        return productQuantities.containsKey(product);
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;
                Discount discount = null;

                int offerTypeValue = offer.getOfferTypeValue();

                if (offer.offerType == SpecialOfferType.TwoForAmount) {
                    discount = getDiscountForXForAmount(p, quantity, offer, unitPrice, quantityAsInt, offerTypeValue);
                }

                int numberOfXs = quantityAsInt / offerTypeValue;
                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(p, "3 for 2", -discountAmount);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount) {
                    discount = getDiscountForXForAmount(p, quantity, offer, unitPrice, quantityAsInt, offerTypeValue);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private Discount getDiscountForXForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int x) {
        if (quantityAsInt < x) return null;

        Discount discount;
        double discountTotal = getDiscountTotalForXForAmount(quantity, offer, unitPrice, quantityAsInt, x);
        discount = new Discount(p, x + " for " + offer.argument, -discountTotal);
        return discount;
    }

    private double getDiscountTotalForXForAmount(double quantity, Offer offer, double unitPrice, int quantityAsInt, int x) {
        double total = offer.argument * (quantityAsInt / x) + quantityAsInt % x * unitPrice;
        return unitPrice * quantity - total;
    }
}
