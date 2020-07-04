package dojo.supermarket.model.abc;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public abstract class DiscountBuilderDecorator implements DiscountBuilder {
    private DiscountBuilder discountBuilder;

    public DiscountBuilderDecorator(DiscountBuilder discountBuilder) {
        this.discountBuilder = discountBuilder;
    }

    @Override
    public Discount build(Product product, double quantity, double unitPrice) {
        return discountBuilder.build(product, quantity, unitPrice);
    }
}
