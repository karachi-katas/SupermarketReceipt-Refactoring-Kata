package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class DiscountFactory {
    
    public static Discount getInstance(SpecialOfferType offerType, Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int x, int numberOfXs) {
        DiscountInstance discountInstance = null;

        if (offerType == SpecialOfferType.TwoForAmount) {
            discountInstance = new TwoForAmount(p, quantity, offer, unitPrice, quantityAsInt, x);
        } else if (offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            discountInstance = new ThreeForTwo(p, quantity, unitPrice, quantityAsInt, numberOfXs);
        } else if (offerType == SpecialOfferType.TenPercentDiscount) {
            discountInstance = new TenPercent(p, quantity, offer, unitPrice);
        } else if (offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            discountInstance = new FiveForAmount(p, quantity, offer, unitPrice, quantityAsInt, x, numberOfXs);
        }

        return DiscountInstance.toDiscount(discountInstance);
    }
}
