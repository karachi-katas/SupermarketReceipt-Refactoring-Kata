package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class FiveForAmountOffer extends Offer {

    double discountAmount;

    public FiveForAmountOffer(Product product,
            double discountAmount) {
        super(product);
        this.discountAmount = discountAmount;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (shoppingCart.getQuantityAsInt(product) >= 5) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / 5;
            double discountTotal = catalog.getUnitPrice(product) * shoppingCart.getQuantity(product) - (
                    discountAmount * numberOfXs + shoppingCart.getQuantityAsInt(product) % 5 * catalog.getUnitPrice(product));
            return new Discount(product, 5 + " for " + discountAmount, -discountTotal);
        }
        return null;
    }
}
