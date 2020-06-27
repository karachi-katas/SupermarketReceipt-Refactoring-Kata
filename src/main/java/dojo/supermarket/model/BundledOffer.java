package dojo.supermarket.model;

import java.util.List;

public class BundledOffer extends Offer {

    List<Product> products;

    public BundledOffer(SpecialOfferType offerType, List<Product> products, double discountPercentageOrAmount) {
        super(offerType, products.get(0), discountPercentageOrAmount);
        this.products = products;
    }


}
