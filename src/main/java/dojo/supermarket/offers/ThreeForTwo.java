package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class ThreeForTwo implements DiscountOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 3;
        double discountAmount =
                quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(p, "3 for 2", -discountAmount);
    }

    @Override
    public boolean isApplicable(SpecialOfferType offerType, int quantityAsInt) {
        return offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2;
    }
}
