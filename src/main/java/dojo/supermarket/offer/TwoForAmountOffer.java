package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public class TwoForAmountOffer extends Offer {

    public TwoForAmountOffer(SpecialOfferType offerType, Product product,
            double discountPercentageOrAmount) {
        super(offerType, product, discountPercentageOrAmount);
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {
        if (shoppingCart.getQuantityAsInt(product) >= 2) {
            double total =
                    discountPercentageOrAmount * (shoppingCart.getQuantityAsInt(product) / 2)
                            + shoppingCart
                            .getQuantityAsInt(
                                    product) % 2 * catalog.getUnitPrice(product);
            double discountN =
                    catalog.getUnitPrice(product) * shoppingCart.getQuantity(product) - total;
            return new Discount(product, "2 for " + discountPercentageOrAmount, -discountN);
        }
        return null;
    }

}
