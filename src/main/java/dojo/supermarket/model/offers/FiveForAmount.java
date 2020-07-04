package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class FiveForAmount extends Offer {
    public FiveForAmount(Product product, double argument) {
        super(product, argument);
    }

    @Override
    public Discount getDiscount(double quantity, double unitPrice) {
        if (quantity < 5) return null;

        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 5;
        double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        return new Discount(product, 5 + " for " + argument, -discountTotal);
    }
}
