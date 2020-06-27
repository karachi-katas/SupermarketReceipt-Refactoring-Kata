package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class TenPercent extends DiscountInstance {
    TenPercent(Product p, double quantity, double offerArgument, double unitPrice) {
        this.product = p;
        this.description = offerArgument + "% off";
        this.discountAmount = -quantity * (unitPrice * offerArgument / 100.0);
    }
}
