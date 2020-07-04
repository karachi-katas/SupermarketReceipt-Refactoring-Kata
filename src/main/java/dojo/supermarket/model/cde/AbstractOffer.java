package dojo.supermarket.model.cde;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public abstract class AbstractOffer {

    private final Product product;
    double argument;

    public AbstractOffer(Product product, double argument) {
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    abstract Discount getDiscount(double quantity, double unitPrice);

}
