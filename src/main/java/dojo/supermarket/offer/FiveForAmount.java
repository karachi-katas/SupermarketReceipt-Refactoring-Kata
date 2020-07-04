package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public class FiveForAmount extends Offer {

    public FiveForAmount(Product product, double argument) {
        super(SpecialOfferType.FiveForAmount, product, argument);
    }

    public Discount getDiscount(double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        if (quantityAsInt >= 5) {
            int x = 5;
            int numberOfXs = quantityAsInt / x;
            double total = argument * numberOfXs + quantityAsInt % x * unitPrice;
            double discountN = unitPrice * quantity - total;
            return new Discount(product, x + " for " + argument, -discountN);
        }
        return null;
    }
}
