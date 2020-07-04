package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class ThreeForTwo extends Offer {

    public ThreeForTwo(Product product, double argument) {
        super(SpecialOfferType.ThreeForTwo, product, argument);
    }

    public Discount getDiscount(double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        if (quantityAsInt > 2) {
            int x = 3;
            int numberOfXs = quantityAsInt / x;
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);

            return new Discount(product, "3 for 2", -discountAmount);
        }
        return null;
    }
}
