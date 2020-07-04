package dojo.supermarket.model.cde;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class TwoForAmount extends Offer {
    public TwoForAmount(Product product, double argument) {
        super(product, argument);
    }

    @Override
    public Discount getDiscount(double quantity, double unitPrice) {
        if (quantity < 2) return null;

        int quantityAsInt = (int) quantity;
        double total = argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        return new Discount(product, "2 for " + argument, -discountN);
    }
}
