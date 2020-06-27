package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class TwoForAmount extends DiscountInstance {

    TwoForAmount(Product p, double quantity, double offerArgument, double unitPrice) {
        int quantityAsInt = (int)quantity;

        double total = offerArgument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        this.product = p;
        this.description = "2 for " + offerArgument;
        this.discountAmount = -discountN;
    }
}
