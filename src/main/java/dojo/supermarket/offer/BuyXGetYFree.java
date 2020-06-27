package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;

public class BuyXGetYFree extends Offer {

    int quantity;
    int free;

    public BuyXGetYFree(Product product, int quantity, int free) {
        super(product);
        this.quantity = quantity;
        this.free = free;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (shoppingCart.getQuantityAsInt(product) > quantity) {
            int totalQuantity = quantity + free;
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / totalQuantity;
            double discountAmount =
                    shoppingCart.getQuantity(product) * catalog.getUnitPrice(product) - (
                            (numberOfXs * quantity * catalog.getUnitPrice(product)) + shoppingCart
                                    .getQuantityAsInt(product) % totalQuantity * catalog.getUnitPrice(product));
            return new Discount(product, totalQuantity + " for " + quantity, -discountAmount);
        }
        return null;
    }

}
