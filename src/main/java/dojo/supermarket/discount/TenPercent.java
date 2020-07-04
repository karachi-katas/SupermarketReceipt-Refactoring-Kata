package dojo.supermarket.discount;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SupermarketCatalog;

public class TenPercent implements DiscountCalculator {
    @Override
    public Discount calculateDiscount(Offer offer, SupermarketCatalog catalog, Product product, double quantity) {
        double unitPrice = catalog.getUnitPrice(product);
        return new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
    }
}
