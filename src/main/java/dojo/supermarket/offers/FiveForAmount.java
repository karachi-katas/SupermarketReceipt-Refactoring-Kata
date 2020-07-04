package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class FiveForAmount implements DiscountOffer {
    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        Discount discount;
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 5;
        double discountTotal =
                unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        return new Discount(p, 5 + " for " + offer.argument, -discountTotal);
    }

    @Override
    public boolean isApplicable(SpecialOfferType offerType, int quantityAsInt) {
        return  offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5 ;
    }
}
