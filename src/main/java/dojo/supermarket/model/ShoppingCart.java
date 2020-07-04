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
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                    discount = getDiscountForTwoForAmount(p, quantity, offer, unitPrice, quantityAsInt, discount);
                }
                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    discount = getDiscountThreeForTwo(p, quantity, unitPrice, quantityAsInt);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = getDiscountTenPercentDiscount(p, quantity, offer, unitPrice);
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    discount = getDiscountForFiveForAmount(p, quantity, offer, unitPrice, quantityAsInt);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private Discount getDiscountForFiveForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt) {
        Discount discount;
        int numberOfXs = quantityAsInt / 5;
        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        discount = new Discount(p, 5 + " for " + offer.argument, -discountTotal);
        return discount;
    }

    private Discount getDiscountTenPercentDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        Discount discount;
        discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        return discount;
    }

    private Discount getDiscountThreeForTwo(Product p, double quantity, double unitPrice, int quantityAsInt) {
        Discount discount;
        int numberOfXs = quantityAsInt / 3;
        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        discount = new Discount(p, "3 for 2", -discountAmount);
        return discount;
    }

    private Discount getDiscountForTwoForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, Discount discount) {
            double total = offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
            double discountN = unitPrice * quantity - total;
            discount = new Discount(p, "2 for " + offer.argument, -discountN);
        return discount;
    }
}
