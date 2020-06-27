package dojo.supermarket.discounts;

import dojo.supermarket.model.Product;

abstract class DiscountInstance {
    String description;
    double discountAmount;
    Product product;

    public static Discount toDiscount(DiscountInstance discountInstance) {
        if (discountInstance == null) {
            return null;
        }
        return new Discount(discountInstance.product, discountInstance.description, discountInstance.discountAmount);
    }
}
