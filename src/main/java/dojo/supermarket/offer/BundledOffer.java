package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BundledOffer extends Offer {

    List<Product> bundledProducts;


    double discountPercentage;

    // set of products

    public BundledOffer(Product product, Product bundledProduct,
            double discountPercentage) {
        super(product);
        this.bundledProducts = Collections.singletonList(bundledProduct);
        this.discountPercentage = discountPercentage;
    }

    public BundledOffer(Product product, List<Product> bundledProducts, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
        this.bundledProducts = bundledProducts;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        int minQuantity = bundledProducts.stream().mapToInt(shoppingCart::getQuantityAsInt).min().getAsInt();
        if (minQuantity > 0) {
            double sum = bundledProducts.stream().mapToDouble(catalog::getUnitPrice).sum();
            double unitPriceForProduct = catalog.getUnitPrice(product);
            double discountPrice = minQuantity * (unitPriceForProduct + sum) * discountPercentage/100;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
