package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class PercentDiscount implements DiscountOffer {
    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        Discount discount;
        return new Discount(p, offer.argument + "% off",
                -quantity * unitPrice * offer.argument / 100.0);
    }

    @Override
    public boolean isApplicable(SpecialOfferType offerType, int quantityAsInt) {
        return offerType == SpecialOfferType.PercentDiscount;
    }
}
