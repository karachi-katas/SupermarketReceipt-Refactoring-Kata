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
        double previousQuantity = isExistingProduct(product) ? productQuantities.get(product) : 0;

        productQuantities.put(product, previousQuantity + quantity);
    }

    private boolean isExistingProduct(Product product) {
        return productQuantities.containsKey(product);
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);

                int quantityAsInt = (int) quantity;
                int offerTypeValue = offer.getOfferTypeValue();

                Discount discount = null;
                if (offer.offerType == SpecialOfferType.TwoForAmount || offer.offerType == SpecialOfferType.FiveForAmount) {
                    discount = getDiscountForXForAmount(p, quantity, offer, unitPrice, quantityAsInt, offerTypeValue);
                }

                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    double discountAmount = quantity * unitPrice - (((quantityAsInt / 3) * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(p, "3 for 2", -discountAmount);
                }

                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }

                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }

    private Discount getDiscountForXForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int offerTypeValue) {
        if (quantityAsInt < offerTypeValue) return null;
        double discountTotal = (unitPrice * quantity) - (offer.argument * (quantityAsInt / offerTypeValue) + quantityAsInt % offerTypeValue * unitPrice);
        return new Discount(p, offerTypeValue + " for " + offer.argument, -discountTotal);
    }


}
