package dojo.supermarket.model;

import dojo.supermarket.type.SpecialOfferType;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public int getQuantityAdjustment() {
        if (offerType == SpecialOfferType.TwoForAmount) {
            return 2;
        }
        if (offerType == SpecialOfferType.ThreeForTwo) {
            return 3;
        }
        if (offerType == SpecialOfferType.FiveForAmount) {
            return 5;
        }
        return 1;
    }

    Product getProduct() {
        return this.product;
    }

}
