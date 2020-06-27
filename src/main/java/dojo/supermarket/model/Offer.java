package dojo.supermarket.model;

public class Offer {
    private SpecialOfferType offerType;
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

    public SpecialOfferType getOfferType() {
        return this.offerType;
    }

    public double getArgument() {
        return argument;
    }
}
