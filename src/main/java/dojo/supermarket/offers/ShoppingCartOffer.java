package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;

public interface ShoppingCartOffer {

    Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice);
}
