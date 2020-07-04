package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class DefaultSpecialOffer extends SpecialOffer {

    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        return null;
    }
}
