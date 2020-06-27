package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class FixedAmountOffer extends Offer {

    double discountAmount;
    int number;

    public FixedAmountOffer(Product product,
            double discountAmount, int number) {
        super(product);
        this.discountAmount = discountAmount;
        this.number = number;

    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (shoppingCart.getQuantityAsInt(product) >= number) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / number;
            double discountTotal = catalog.getUnitPrice(product) * shoppingCart.getQuantity(product) - (
                    discountAmount * numberOfXs + shoppingCart.getQuantityAsInt(product) % number * catalog.getUnitPrice(product));
            return new Discount(product, number + " for " + discountAmount, -discountTotal);
        }
        return null;
    }
}
