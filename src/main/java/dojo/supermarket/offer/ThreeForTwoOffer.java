package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class ThreeForTwoOffer extends Offer {

    int quantityPurchased;
    int quantityInOffer;

    public ThreeForTwoOffer(Product product, int quantityPurchased, int quantityInOffer) {
        super(product);
        this.quantityPurchased = quantityPurchased;
        this.quantityInOffer = quantityInOffer;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (shoppingCart.getQuantityAsInt(product) > quantityPurchased) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / quantityInOffer;
            double discountAmount =
                    shoppingCart.getQuantity(product) * catalog.getUnitPrice(product) - (
                            (numberOfXs * quantityPurchased * catalog.getUnitPrice(product)) + shoppingCart
                                    .getQuantityAsInt(product) % quantityInOffer * catalog.getUnitPrice(product));
            return new Discount(product, quantityInOffer + " for " + quantityPurchased, -discountAmount);
        }
        return null;
    }

}
