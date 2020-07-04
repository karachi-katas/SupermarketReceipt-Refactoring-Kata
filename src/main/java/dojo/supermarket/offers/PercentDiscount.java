package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public class PercentDiscount implements ShoppingCartOffer {
    @Override
    public Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        Discount discount;
        return new Discount(p, offer.argument + "% off",
                -quantity * unitPrice * offer.argument / 100.0);
    }
}
