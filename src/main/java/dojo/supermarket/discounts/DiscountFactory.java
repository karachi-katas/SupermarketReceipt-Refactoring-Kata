package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class DiscountFactory {
    
    public static Discount getInstance(SpecialOfferType offerType, Product p, double quantity, Offer offer, double unitPrice) {
        DiscountInstance discountInstance = null;

        if (offerType == SpecialOfferType.TwoForAmount && quantity >= 2) {
            discountInstance = new TwoForAmount(p, quantity, offer, unitPrice);
        } else if (offerType == SpecialOfferType.ThreeForTwo && quantity > 2) {
            discountInstance = new ThreeForTwo(p, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.TenPercentDiscount) {
            discountInstance = new TenPercent(p, quantity, offer, unitPrice);
        } else if (offerType == SpecialOfferType.FiveForAmount && quantity >= 5) {
            discountInstance = new FiveForAmount(p, quantity, offer, unitPrice);
        }

        return DiscountInstance.toDiscount(discountInstance);
    }
}
