package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class TenPercentDiscount extends Offer {
    public TenPercentDiscount(Product product, double argument) {
        super(SpecialOfferType.TenPercentDiscount, product, argument);
    }

    public Discount getDiscount(double quantity, double unitPrice) {
        return new Discount(product, argument + "% off", -quantity * unitPrice * argument / 100.0);
    }
}
