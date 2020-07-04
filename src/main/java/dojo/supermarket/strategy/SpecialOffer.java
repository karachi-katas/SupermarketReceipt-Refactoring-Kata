package dojo.supermarket.strategy;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public abstract class SpecialOffer {
    public abstract Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice);
}
