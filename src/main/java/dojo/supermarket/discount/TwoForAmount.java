package dojo.supermarket.discount;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SupermarketCatalog;

public class TwoForAmount implements DiscountCalculator {
    @Override
    public Discount calculateDiscount(Offer offer, SupermarketCatalog catalog, Product product, double quantity) {
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;

        int quantityAdjustment = offer.getQuantityAdjustment();

        int numberOfXs = quantityAsInt / quantityAdjustment;
        double total = offer.argument * (quantityAsInt / quantityAdjustment) + quantityAsInt % 2 * unitPrice;
        double discountN = unitPrice * quantity - total;
        return new Discount(product, "2 for " + offer.argument, -discountN);
    }
}
