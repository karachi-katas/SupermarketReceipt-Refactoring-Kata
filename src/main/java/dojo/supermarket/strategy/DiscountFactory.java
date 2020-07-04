package dojo.supermarket.strategy;

import dojo.supermarket.model.SpecialOfferType;

public class DiscountFactory {
    private static SpecialOffer xForAmountSpecialOffer = new XForAmountSpecialOffer();
    private static SpecialOffer xForYSpecialOffer = new XForYSpecialOffer();
    private static SpecialOffer xForPercentageSpecialOffer = new XForPercentageSpecialOffer();
    private static SpecialOffer defaultSpecialOffer = new DefaultSpecialOffer();

    public static SpecialOffer getSpecialOffer(SpecialOfferType offerType) {
        if (offerType == SpecialOfferType.TwoForAmount || offerType == SpecialOfferType.FiveForAmount) {
            return xForAmountSpecialOffer;
        }

        if (offerType == SpecialOfferType.ThreeForTwo) {
            return xForYSpecialOffer;
        }

        if (offerType == SpecialOfferType.TenPercentDiscount) {
            return xForPercentageSpecialOffer;
        }
        return defaultSpecialOffer;
    }
}
