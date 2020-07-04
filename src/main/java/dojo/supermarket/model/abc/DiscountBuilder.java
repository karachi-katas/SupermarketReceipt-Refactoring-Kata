package dojo.supermarket.model.abc;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public interface DiscountBuilder {
    Discount build(Product product, double quantity, double unitPrice);
}
