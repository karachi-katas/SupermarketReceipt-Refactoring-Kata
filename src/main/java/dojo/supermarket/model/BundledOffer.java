package dojo.supermarket.model;

import java.util.List;

public class BundledOffer extends Offer {

    List<Product> products;

    public BundledOffer(SpecialOfferType offerType, List<Product> products, double discountPercentageOrAmount) {
        super(offerType, products.get(0), discountPercentageOrAmount);
        this.products = products;
    }

    @Override
    Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {
        Product otherProduct = products.get(1);
        if (shoppingCart.productQuantities.containsKey(otherProduct)) {
            double discountPrice = (catalog.getUnitPrice(product)+catalog.getUnitPrice(otherProduct))*0.10;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
