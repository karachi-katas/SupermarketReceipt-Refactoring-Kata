package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class FiveForAmount extends DiscountInstance {
    public FiveForAmount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int numberOfXs = quantityAsInt / 5;

        double discountTotal = unitPrice * quantity - (offer.getArgument() * numberOfXs + quantityAsInt % 5 * unitPrice);
        this.product = p;
        this.description = "5 for " + offer.getArgument();
        this.discountAmount = -discountTotal;
    }
}
