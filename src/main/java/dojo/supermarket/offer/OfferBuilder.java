package dojo.supermarket.offer;

import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;
import java.util.ArrayList;
import java.util.List;

public class OfferBuilder {

    private SpecialOfferType offerType;
    private List<Product> products = new ArrayList<>();
    private double discountPercentageOrAmount;

    public OfferBuilder(SpecialOfferType offerType, Product product,
            double discountPercentageOrAmount) {
        this.offerType = offerType;
        this.discountPercentageOrAmount = discountPercentageOrAmount;
        this.products.add(product);
    }

    public OfferBuilder(Product product) {
        this.products.add(product);
    }

    public Offer createOffer() {
        switch (offerType) {
            case PercentDiscount:
                return new PercentDiscountOffer(products.get(0), discountPercentageOrAmount);
        }
        throw new UnsupportedOperationException();
    }

    public Offer create(double discountAmount, int quantity) {
        return new FixedAmountOffer(products.get(0), discountAmount, quantity);
    }

    public Offer create(Product bundledProduct, double discountPercentage) {
        return new BundledOffer(products.get(0), bundledProduct, discountPercentage);
    }

    public Offer create(int quantityPurchased, int quantityInOffer) {
        return new ThreeForTwoOffer(products.get(0), quantityPurchased, quantityInOffer);
    }
}