package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public class FiveForAmountOffer extends Offer {

    public FiveForAmountOffer(SpecialOfferType offerType, Product product,
            double discountPercentageOrAmount) {
        super(offerType, product, discountPercentageOrAmount);
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (offerType == SpecialOfferType.FiveForAmount && shoppingCart.getQuantityAsInt(product) >= 5) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / 5;
            double discountTotal = catalog.getUnitPrice(product) * shoppingCart.getQuantity(product) - (
                    discountPercentageOrAmount * numberOfXs + shoppingCart.getQuantityAsInt(product) % 5 * catalog.getUnitPrice(product));
            return new Discount(product, 5 + " for " + discountPercentageOrAmount, -discountTotal);
        }
        return null;
    }
}
