package dojo.supermarket.model.abc;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class SimpleDiscountBuilder implements DiscountBuilder{
    @Override
    public Discount build(Product product, double quantity, double unitPrice) {
        return null;
    }
}
