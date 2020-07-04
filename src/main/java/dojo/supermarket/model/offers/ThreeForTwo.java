package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class ThreeForTwo extends Offer {
    public ThreeForTwo(Product product, double argument) {
        super(product, argument);
    }

    @Override
    public Discount getDiscount(double quantity, double unitPrice) {
        if (quantity < 2) return null;

        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 3;
        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(product, "3 for 2", -discountAmount);
    }
}
