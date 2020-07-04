package dojo.supermarket.model;

import dojo.supermarket.model.abc.DiscountBuilderDecorator;
import dojo.supermarket.model.abc.ThreeForTwoDecorator;

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
            Optional.ofNullable(getDiscountBasedOnOffer(p, quantity, offer, unitPrice)).ifPresent(receipt::addDiscount);
        }
    }

    private boolean isOfferApplicableOnProduct(Map<Product, Offer> offers, Product p) {
        return offers.containsKey(p);
    }

    private Discount getDiscountBasedOnOffer(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        DiscountBuilderDecorator discountBuilderDecorator = new ThreeForTwoDecorator().build()
        if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2)
            return getDiscountForTwoForAmountOffer(p, quantity, offer, unitPrice);
        else if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2)
            return getDiscountForThreeForTwoOffer(p, quantity, offer, unitPrice);
        else if (offer.offerType == SpecialOfferType.TenPercentDiscount)
            return getDiscountForTenPercentDiscountOffer(p, quantity, offer, unitPrice);
        else if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5)
            return getDiscountForFiveForAmountOffer(p, quantity, offer, unitPrice);
        return null;
    }

    private Discount getDiscountForFiveForAmountOffer(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;

        int numberOfXs = quantityAsInt / 5;
        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        return new Discount(p, 5 + " for " + offer.argument, -discountTotal);
    }

    private Discount getDiscountForTenPercentDiscountOffer(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;

        return new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
    }

    private Discount getDiscountForThreeForTwoOffer(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;

        int numberOfXs = quantityAsInt / 3;
        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(p, "3 for 2", -discountAmount);
    }

    private Discount getDiscountForTwoForAmountOffer(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        double total = offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        return new Discount(p, "2 for " + offer.argument, -discountN);
    }
}
