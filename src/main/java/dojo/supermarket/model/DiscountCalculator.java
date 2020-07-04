package dojo.supermarket.model;

public interface DiscountCalculator {
    Discount calculateDiscount(Offer offer,SupermarketCatalog catalog, Product product, double quantity);
}
