package dojo.supermarket.discounts;

import dojo.supermarket.model.Product;

import java.util.Objects;

public class Discount {
    private final String description;
    private final double discountAmount;
    private final Product product;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.discountAmount, discountAmount) == 0 &&
                description.equals(discount.description) &&
                product.equals(discount.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, discountAmount, product);
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "description='" + description + '\'' +
                ", discountAmount=" + discountAmount +
                ", product=" + product +
                '}';
    }
}
