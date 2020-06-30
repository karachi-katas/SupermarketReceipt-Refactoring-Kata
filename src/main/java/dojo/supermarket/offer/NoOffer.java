package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class NoOffer extends Offer {

    public NoOffer(Product product) {
        super(product);
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product,
            ShoppingCart shoppingCart) {
        return null;
    }
}
