package dojo.supermarket.model;

import dojo.supermarket.offer.TenPercentDiscount;
import dojo.supermarket.offer.ThreeForTwo;
import dojo.supermarket.offer.TwoForAmount;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    protected double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    void applyDiscount(Receipt receipt, SupermarketCatalog catalog,
                       double quantity) {
        Product product = getProduct();
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new TenPercentDiscount(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.ThreeForTwo) {
            discount = new ThreeForTwo(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        } else if (offerType == SpecialOfferType.TwoForAmount) {
            discount = new TwoForAmount(this.product, this.argument).getDiscount(product, quantity, unitPrice);
        }
        else {
            if (offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                int x = 5;
                int numberOfXs = quantityAsInt / x;
                double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                discount = new Discount(product, x + " for " + argument, -discountTotal);
            }
        }
        if (discount != null)
            receipt.addDiscount(discount);
    }
}
