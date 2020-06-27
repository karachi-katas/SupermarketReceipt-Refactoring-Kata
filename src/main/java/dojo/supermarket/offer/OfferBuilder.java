package dojo.supermarket.offer;

import dojo.supermarket.model.Product;
import java.util.ArrayList;
import java.util.List;

public class OfferBuilder {

    private List<Product> products = new ArrayList<>();

    public OfferBuilder(Product product) {
        this.products.add(product);
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

    public Offer create(double discountPercentage) {
        return new PercentDiscountOffer(products.get(0), discountPercentage);
    }
}