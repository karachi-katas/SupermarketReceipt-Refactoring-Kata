package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class XforYSpecialOffer extends SpecialOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int offerSourceQuantity = offer.getOfferSourceQuantity();
        int offerTargetQuantity = offer.getOfferTargetQuantity();
        if (quantityAsInt < offerSourceQuantity) return null;

        double discount = quantity * unitPrice - (((quantityAsInt / offerSourceQuantity) * offerTargetQuantity * unitPrice) + quantityAsInt % offerSourceQuantity * unitPrice);
        return new Discount(p, offerSourceQuantity + " for " + offerTargetQuantity, -discount);
    }
}
