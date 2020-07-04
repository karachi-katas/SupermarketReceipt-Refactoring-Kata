package dojo.supermarket.model;

import dojo.supermarket.discount.Discount;
import dojo.supermarket.discount.DiscountCalculator;

public class ThreeForTwo implements DiscountCalculator {
    @Override
    public Discount calculateDiscount(Offer offer, SupermarketCatalog catalog, Product product , double quantity) {

            double unitPrice = catalog.getUnitPrice(product);
            int quantityAsInt = (int) quantity;

            int quantityAdjustment = offer.getQuantityAdjustment();

            int numberOfXs = quantityAsInt / quantityAdjustment;

            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            return new Discount(product, "3 for 2", -discountAmount);
    }
}
