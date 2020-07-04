package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class XForPercentageSpecialOffer extends SpecialOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        return new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
    }
}
