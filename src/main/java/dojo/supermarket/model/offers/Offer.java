package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public abstract class Offer {
    protected final Product product;
    protected double argument;

    public Offer(Product product, double argument) {
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public abstract Discount getDiscount(double quantity, double unitPrice);

}
