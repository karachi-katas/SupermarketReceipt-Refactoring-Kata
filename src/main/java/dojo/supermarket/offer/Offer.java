package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public abstract class Offer {
    SpecialOfferType offerType;
    private final Product product;

    public Offer(SpecialOfferType offerType, Product product) {
        this.offerType = offerType;
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public abstract Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart);
}
