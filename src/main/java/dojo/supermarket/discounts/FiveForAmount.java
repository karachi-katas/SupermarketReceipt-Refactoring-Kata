package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class FiveForAmount extends DiscountInstance {
    public FiveForAmount(Product p, double quantity, double offerArgument, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int numberOfXs = quantityAsInt / 5;

        double discountTotal = unitPrice * quantity - (offerArgument * numberOfXs + quantityAsInt % 5 * unitPrice);
        this.product = p;
        this.description = "5 for " + offerArgument;
        this.discountAmount = -discountTotal;
    }
}
