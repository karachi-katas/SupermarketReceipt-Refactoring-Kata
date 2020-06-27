package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class FiveForAmount extends DiscountInstance {
    public FiveForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int x, int numberOfXs) {
        super();
        double discountTotal = unitPrice * quantity - (offer.getArgument() * numberOfXs + quantityAsInt % 5 * unitPrice);
        this.product = p;
        this.description = x + " for " + offer.getArgument();
        this.discountAmount = -discountTotal;
    }
}
