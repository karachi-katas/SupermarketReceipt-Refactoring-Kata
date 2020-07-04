package dojo.supermarket.discount;

import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SupermarketCatalog;

public interface DiscountCalculator {
    Discount calculateDiscount(Offer offer, SupermarketCatalog catalog, Product product, double quantity);
}
