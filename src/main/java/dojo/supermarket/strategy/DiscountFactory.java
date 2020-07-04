package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.SpecialOfferType;

public class DiscountFactory {


    public static SpecialOffer getSpecialOffer(SpecialOfferType offerType) {
        if (offerType == SpecialOfferType.TwoForAmount || offerType == SpecialOfferType.FiveForAmount) {
            return new XForAmountSpecialOffer();
        }

        if (offerType == SpecialOfferType.ThreeForTwo) {
            return new XforYSpecialOffer();
        }

        if (offerType == SpecialOfferType.TenPercentDiscount) {
            return new XForPercentageSpecialOffer();
        }
        return new DefaultSpecialOffer();
    }
}
