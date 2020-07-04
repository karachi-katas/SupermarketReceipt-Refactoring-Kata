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

                Discount discount = null;
                if (offer.offerType == SpecialOfferType.TwoForAmount || offer.offerType == SpecialOfferType.FiveForAmount) {
                    discount = getDiscountForXForAmount(p, quantity, offer, unitPrice);
                }

                if (offer.offerType == SpecialOfferType.ThreeForTwo) {
                    discount = getDiscountForXForY(p, quantity, offer, unitPrice);
                }

                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = getDiscountForPercentage(p, quantity, offer, unitPrice);
                }

                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }

    private Discount getDiscountForPercentage(Product p, double quantity, Offer offer, double unitPrice) {
        return new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
    }

    private Discount getDiscountForXForY(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int offerTypeValue = offer.getOfferTypeValue();
        if (quantityAsInt < offerTypeValue) return null;

        double discount = quantity * unitPrice - (((quantityAsInt / offerTypeValue) * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(p, offerTypeValue + " for 2", -discount);
    }

    private Discount getDiscountForXForAmount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int offerTypeValue = offer.getOfferTypeValue();
        if (quantityAsInt < offerTypeValue) return null;
        
        double discount = (unitPrice * quantity) - (offer.argument * (quantityAsInt / offerTypeValue) + quantityAsInt % offerTypeValue * unitPrice);
        return new Discount(p, offerTypeValue + " for " + offer.argument, -discount);
    }


}
