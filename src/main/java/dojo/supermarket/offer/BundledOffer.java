package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;
import java.util.List;

public class BundledOffer extends Offer {

    List<Product> products;

    public BundledOffer(SpecialOfferType offerType, List<Product> products, double discountPercentageOrAmount) {
        super(offerType, products.get(0), discountPercentageOrAmount);
        this.products = products;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {
        Product otherProduct = products.get(1);
        if (shoppingCart.getProductQuantities().containsKey(otherProduct)) {
            double discountPrice = (catalog.getUnitPrice(product)+catalog.getUnitPrice(otherProduct))*0.10;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
