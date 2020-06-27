package dojo.supermarket.offer;

import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;
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
            case TwoForAmount:
                return new TwoForAmountOffer(offerType, products.get(0), discountPercentageOrAmount);
            case ThreeForTwo:
                return new ThreeForTwoOffer(offerType, products.get(0), discountPercentageOrAmount);
            case PercentDiscount:
                return new PercentDiscountOffer(offerType, products.get(0), discountPercentageOrAmount);
            case FiveForAmount:
                return new FiveForAmountOffer(offerType, products.get(0), discountPercentageOrAmount);
        }
        throw new UnsupportedOperationException();
    }
}