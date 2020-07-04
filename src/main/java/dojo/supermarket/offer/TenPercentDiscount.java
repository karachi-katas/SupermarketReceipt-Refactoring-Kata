package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class TenPercentDiscount extends Offer {
    public TenPercentDiscount(Product product, double argument) {
        super(product, argument);
    }

    public Discount getDiscount(double quantity, double unitPrice) {
        return new Discount(product, argument + "% off", -quantity * unitPrice * argument / 100.0);
    }
}
