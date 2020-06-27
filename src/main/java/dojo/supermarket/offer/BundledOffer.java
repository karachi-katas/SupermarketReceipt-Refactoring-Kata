package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;
import java.util.List;

public class BundledOffer extends Offer {

    List<Product> products;
    double discountPercentage;

    public BundledOffer(List<Product> products,
            double discountPercentage) {
        super(products.get(0));
        this.products = products;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {
        Product otherProduct = products.get(1);
        if (shoppingCart.getProductQuantities().containsKey(otherProduct)) {
            double discountPrice = (catalog.getUnitPrice(product)+catalog.getUnitPrice(otherProduct)) * discountPercentage/100;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
