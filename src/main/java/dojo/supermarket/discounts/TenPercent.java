package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class TenPercent extends DiscountInstance {
    TenPercent(Product p, double quantity, Offer offer, double unitPrice) {
        this.product = p;
        this.description = offer.getArgument() + "% off";
        this.discountAmount = -quantity * (unitPrice * offer.getArgument() / 100.0);
    }
}
