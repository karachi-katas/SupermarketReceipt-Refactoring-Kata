package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class DiscountFactory {
    
    public static Discount getInstance(Offer offer, Product p, double quantity, double unitPrice) {
        DiscountInstance discountInstance = null;

        if (offer.getOfferType() == SpecialOfferType.TwoForAmount && quantity >= 2) {
            discountInstance = new TwoForAmount(p, quantity, offer.getArgument(), unitPrice);
        } else if (offer.getOfferType() == SpecialOfferType.ThreeForTwo && quantity > 2) {
            discountInstance = new ThreeForTwo(p, quantity, unitPrice);
        } else if (offer.getOfferType() == SpecialOfferType.TenPercentDiscount) {
            discountInstance = new TenPercent(p, quantity, offer.getArgument(), unitPrice);
        } else if (offer.getOfferType() == SpecialOfferType.FiveForAmount && quantity >= 5) {
            discountInstance = new FiveForAmount(p, quantity, offer.getArgument(), unitPrice);
        }

        return DiscountInstance.toDiscount(discountInstance);
    }
}
