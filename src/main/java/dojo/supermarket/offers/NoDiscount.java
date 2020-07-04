package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class NoDiscount implements DiscountOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        return null;
    }

    @Override
    public boolean isApplicable(SpecialOfferType offerType, int quantityAsInt) {
        return false;
    }
}
