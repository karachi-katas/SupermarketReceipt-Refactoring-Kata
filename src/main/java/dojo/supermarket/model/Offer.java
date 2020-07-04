package dojo.supermarket.model;

import dojo.supermarket.offer.FiveForAmount;
import dojo.supermarket.offer.TenPercentDiscount;
import dojo.supermarket.offer.ThreeForTwo;
import dojo.supermarket.offer.TwoForAmount;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    protected double argument;

    protected Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public static Offer createOffer(SpecialOfferType offerType, Product product, double argument) {
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            return new TenPercentDiscount(product, argument);
        } else if (offerType == SpecialOfferType.ThreeForTwo) {
            return new ThreeForTwo(product, argument);
        } else if (offerType == SpecialOfferType.TwoForAmount) {
            return new TwoForAmount(product, argument);
        } else if (offerType == SpecialOfferType.FiveForAmount) {
            return new FiveForAmount(product, argument);
        }
        throw new UnsupportedOperationException();
    }

    Product getProduct() {
        return this.product;
    }

    void applyDiscount(Receipt receipt, SupermarketCatalog catalog,
                       double quantity) {
        Product product = getProduct();
        double unitPrice = catalog.getUnitPrice(product);
        Discount discount = null;
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new TenPercentDiscount(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.ThreeForTwo) {
            discount = new ThreeForTwo(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.TwoForAmount) {
            discount = new TwoForAmount(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.FiveForAmount) {
            discount = new FiveForAmount(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        }
        if (discount != null)
            receipt.addDiscount(discount);
    }
}
