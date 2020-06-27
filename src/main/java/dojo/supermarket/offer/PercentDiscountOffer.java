package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SpecialOfferType;
import dojo.supermarket.model.SupermarketCatalog;

public class PercentDiscountOffer extends Offer {

    private double discountPercentage;

    public PercentDiscountOffer(SpecialOfferType offerType,
            Product product, double discountPercentageOrAmount) {
        super(offerType, product);
        this.discountPercentage = discountPercentageOrAmount;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product,
            ShoppingCart shoppingCart) {

        return new Discount(product, discountPercentage + "% off", -shoppingCart
                .getQuantity(product) * catalog.getUnitPrice(product)
                * discountPercentage / 100.0);
    }
}
