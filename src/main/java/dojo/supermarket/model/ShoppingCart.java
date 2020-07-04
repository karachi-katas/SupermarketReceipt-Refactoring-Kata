package dojo.supermarket.model;

import dojo.supermarket.offers.*;

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


                ShoppingCartOffer shoppingCartOffer = getOfferTypeAndDiscount(offer.offerType, quantityAsInt);
                if (shoppingCartOffer != null)
                    discount = shoppingCartOffer.getDiscount(p, quantity, offer, unitPrice);

//                if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
//                    discount = new TwoForAmountOffer().getDiscount(p, quantity, offer, unitPrice);
//                }
//                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
//                    discount = new ThreeForTwo().getDiscount(p, quantity,offer, unitPrice);
//                }
//                if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
//                    discount = new FiveForAmount().getDiscount(p, quantity, offer, unitPrice);
//                }
//                if (offer.offerType == SpecialOfferType.PercentDiscount) {
//                    discount = new PercentDiscount().getDiscount(p, quantity, offer, unitPrice);
//                }

                if (discount != null) {
                    receipt.addDiscount(discount);
                }
            }

        }
    }

    private ShoppingCartOffer getOfferTypeAndDiscount(SpecialOfferType offerType, int quantityAsInt) {

        if (offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
            return new TwoForAmountOffer();
        }
        if (offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            return  new ThreeForTwo();
        }
        if (offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            return new FiveForAmount();
        }
        if (offerType == SpecialOfferType.PercentDiscount) {
            return new PercentDiscount();
        }

        return null;

    }

    private Discount getDiscountForFiveForAmount(Product p, double quantity, Offer offer,
        double unitPrice) {
        Discount discount;
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 5;
        double discountTotal =
            unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        return new Discount(p, 5 + " for " + offer.argument, -discountTotal);
    }

    private Discount getDiscountTenPercentDiscount(Product p, double quantity, Offer offer,
        double unitPrice) {
        Discount discount;
        return new Discount(p, offer.argument + "% off",
            -quantity * unitPrice * offer.argument / 100.0);
    }

    private Discount getDiscountThreeForTwo(Product p, double quantity,Offer offer, double unitPrice) {
        Discount discount;
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 3;
        double discountAmount =
            quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(p, "3 for 2", -discountAmount);
    }

    private Discount getDiscountForTwoForAmount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        double total = offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        return new Discount(p, "2 for " + offer.argument, -discountN);
    }
}
