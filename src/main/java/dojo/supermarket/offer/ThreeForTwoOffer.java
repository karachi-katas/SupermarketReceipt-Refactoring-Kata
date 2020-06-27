package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public class ThreeForTwoOffer extends Offer {

    public ThreeForTwoOffer(SpecialOfferType offerType, Product product,
            double discountPercentageOrAmount) {
        super(offerType, product, discountPercentageOrAmount);
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (shoppingCart.getQuantityAsInt(product) > 2) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / 3;
            double discountAmount =
                    shoppingCart.getQuantity(product) * catalog.getUnitPrice(product) - (
                            (numberOfXs * 2 * catalog.getUnitPrice(product)) + shoppingCart
                                    .getQuantityAsInt(product) % 3 * catalog.getUnitPrice(product));
            return new Discount(product, "3 for 2", -discountAmount);
        }
        return null;
    }

}
