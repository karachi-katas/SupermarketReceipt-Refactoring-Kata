package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double discountPercentageOrAmount;

    public Offer(SpecialOfferType offerType, Product product, double discountPercentageOrAmount) {
        this.offerType = offerType;
        this.discountPercentageOrAmount = discountPercentageOrAmount;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

}
