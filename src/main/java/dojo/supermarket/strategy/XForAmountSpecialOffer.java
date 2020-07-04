package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class XForAmountSpecialOffer extends SpecialOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int offerSourceQuantity = offer.getOfferSourceQuantity();
        if (quantityAsInt < offerSourceQuantity) return null;

        double discount = (unitPrice * quantity) - (offer.argument * (quantityAsInt / offerSourceQuantity) + quantityAsInt % offerSourceQuantity * unitPrice);
        return new Discount(p, offerSourceQuantity + " for " + offer.argument, -discount);
    }
}
