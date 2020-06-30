package dojo.supermarket.offer;

import dojo.supermarket.model.BundleOfferWithNoBundledProducts;
import dojo.supermarket.model.Product;
import java.util.Collections;
import java.util.List;

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

    public Offer create(List<Product> bundledProducts, double discountPercentage) throws BundleOfferWithNoBundledProducts {
        if (bundledProducts.isEmpty()) {
            throw new BundleOfferWithNoBundledProducts();
        }
        return new BundledOffer(product, bundledProducts, discountPercentage);
    }

    public Offer create(int quantity, int free) {
        return new BuyXGetYFree(product, quantity, free);
    }

    public Offer create(double discountPercentage) {
        return new PercentDiscountOffer(product, discountPercentage);
    }
}