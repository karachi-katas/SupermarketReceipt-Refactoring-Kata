package dojo.supermarket.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Offer;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.SpecialOfferType;

public interface DiscountOffer {

    Discount getDiscount(Product p, double quantity, Offer offer, double unitPrice);

    boolean isApplicable(SpecialOfferType offerType, int quantityAsInt);
}
