package dojo.supermarket.discounts;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class TwoForAmount extends DiscountInstance {

    TwoForAmount(Product p, double quantity, Offer offer, double unitPrice) {
        int quantityAsInt = (int)quantity;

        double total = offer.getArgument() * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        this.product = p;
        this.description = "2 for " + offer.getArgument();
        this.discountAmount = -discountN;
    }
}
