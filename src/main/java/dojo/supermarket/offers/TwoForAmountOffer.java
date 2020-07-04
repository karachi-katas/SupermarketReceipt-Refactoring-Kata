package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class TwoForAmountOffer implements ShoppingCartOffer {


    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        double total = offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        return new Discount(p, "2 for " + offer.argument, -discountN);
    }

    @Override
    public boolean isApplicable(SpecialOfferType offerType, int quantityAsInt) {
        return offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2;
    }
}
