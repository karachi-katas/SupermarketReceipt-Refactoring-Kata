package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class TwoForAmount extends Offer {

    public TwoForAmount(Product product, double argument) {
        super(SpecialOfferType.TwoForAmount, product, argument);
    }

    public Discount getDiscount(Product product, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        if (quantityAsInt >= 2) {
            int x = 2;
            int numberOfXs = quantityAsInt / x;
            double total = argument * numberOfXs + quantityAsInt % 2 * unitPrice;
            double discountN = unitPrice * quantity - total;
            return new Discount(product, "2 for " + argument, -discountN);
        }
        return null;
    }
}
