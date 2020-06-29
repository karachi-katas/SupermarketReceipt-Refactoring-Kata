package dojo.supermarket.model;

import java.util.List;

public class Offer {
    private List<Product> products;
    private DiscountBuilder discountBuilder;

    public Offer(List<Product> products, DiscountBuilder discountBuilder) {
        this.products = products;
        this.discountBuilder = discountBuilder;
    }

    public List<Product> getProducts() {
        return products;
    }

    public DiscountBuilder getDiscountBuilder() {
        return discountBuilder;
    }
}
