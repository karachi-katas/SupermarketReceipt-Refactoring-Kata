package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double discountPercentageOrAmount;

    public Offer(SpecialOfferType offerType, Product product, double discountPercentageOrAmount) {
        this.offerType = offerType;
        this.discountPercentageOrAmount = discountPercentageOrAmount;
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public Discount getDiscount(SupermarketCatalog catalog, Product product, ShoppingCart shoppingCart) {

        if (offerType == SpecialOfferType.PercentDiscount) {
            return new Discount(product, discountPercentageOrAmount + "% off", -shoppingCart
                    .getQuantity(product) * catalog.getUnitPrice(product) * discountPercentageOrAmount / 100.0);
        }
        if (offerType == SpecialOfferType.FiveForAmount && shoppingCart.getQuantityAsInt(product) >= 5) {
            int numberOfXs = shoppingCart.getQuantityAsInt(product) / 5;
            double discountTotal = catalog.getUnitPrice(product) * shoppingCart.getQuantity(product) - (
                    discountPercentageOrAmount * numberOfXs + shoppingCart.getQuantityAsInt(product) % 5 * catalog.getUnitPrice(product));
            return new Discount(product, 5 + " for " + discountPercentageOrAmount, -discountTotal);
        }
        return null;
    }
}
