package dojo.supermarket.offer;

import dojo.supermarket.model.Product;

public class OfferBuilder {

    private Product product;

    public OfferBuilder(Product product) {
        this.product = product;
    }

    public Offer create(double discountAmount, int quantity) {
        return new FixedAmountOffer(product, discountAmount, quantity);
    }

    public Offer create(Product bundledProduct, double discountPercentage) {
        return new BundledOffer(product, bundledProduct, discountPercentage);
    }

    public Offer create(int quantityPurchased, int quantityInOffer) {
        return new ThreeForTwoOffer(product, quantityPurchased, quantityInOffer);
    }

    public Offer create(double discountPercentage) {
        return new PercentDiscountOffer(product, discountPercentage);
    }
}