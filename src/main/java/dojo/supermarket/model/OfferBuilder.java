package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;

public class OfferBuilder {

    private SpecialOfferType offerType;
    private List<Product> products = new ArrayList<>();
    private double discountPercentageOrAmount;

    public OfferBuilder(SpecialOfferType offerType, Product product, double discountPercentageOrAmount) {
        this.offerType = offerType;
        this.discountPercentageOrAmount = discountPercentageOrAmount;
        this.products.add(product);
    }

    public OfferBuilder addProduct(Product product) {
        this.products.add(product);
        return this;
    }

    public Offer createOffer() {
        switch (offerType) {
            case BundledDiscount:
                assert products.size() > 1;
                return new BundledOffer(offerType, products, discountPercentageOrAmount);
        }

        assert products.size() == 1;
        return new Offer(offerType, products.get(0), discountPercentageOrAmount);
    }
}