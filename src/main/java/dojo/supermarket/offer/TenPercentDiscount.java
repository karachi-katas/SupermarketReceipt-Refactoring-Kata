package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class TenPercentDiscount extends Offer {
    public TenPercentDiscount(SpecialOfferType offerType, Product product, double argument) {
        super(offerType, product, argument);
    }

    protected Discount getDiscount(Product p, double quantity, double unitPrice) {
        return new Discount(p, argument + "% off", -quantity * unitPrice * argument / 100.0);
    }
}
