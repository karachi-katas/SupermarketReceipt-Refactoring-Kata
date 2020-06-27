package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class BundledOffer extends Offer {

    Product bundledProduct;
    double discountPercentage;

    // set of products

    public BundledOffer(Product product, Product bundledProduct,
            double discountPercentage) {
        super(product);
        this.bundledProduct = bundledProduct;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {
        if (shoppingCart.isProductAvailableForOffer(bundledProduct)
                && shoppingCart.getProductQuantities().containsKey(bundledProduct)) {
            shoppingCart.setProductAvailedForOffer(bundledProduct);
            double discountPrice = (catalog.getUnitPrice(product)+catalog.getUnitPrice(bundledProduct)) * discountPercentage/100;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
