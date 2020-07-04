package dojo.supermarket.discount;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SupermarketCatalog;

public class FiveForAmount implements DiscountCalculator {
    @Override
    public Discount calculateDiscount(Offer offer, SupermarketCatalog catalog, Product product, double quantity) {
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;

        int quantityAdjustment = offer.getQuantityAdjustment();

        int numberOfXs = quantityAsInt / quantityAdjustment;
        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        return new Discount(product, quantityAdjustment + " for " + offer.argument, -discountTotal);
    }
}
