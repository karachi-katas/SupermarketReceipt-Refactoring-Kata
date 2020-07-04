package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public int getOfferSourceQuantity() {
        return this.offerType.sourceQuantity;
    }

    public int getOfferTargetQuantity() {
        return this.offerType.targetQuantity;
    }

}
