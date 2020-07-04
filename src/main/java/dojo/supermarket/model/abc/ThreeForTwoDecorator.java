package dojo.supermarket.model.abc;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class ThreeForTwoDecorator extends DiscountBuilderDecorator {

    public ThreeForTwoDecorator(DiscountBuilder discountBuilder) {
        super(discountBuilder);
    }

    @Override
    public Discount build(Product product, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / 3;
        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(product, "3 for 2", -discountAmount);
    }
}
