package dojo.supermarket.model;

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

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Discount discount = (Discount) o;

        if (Double.compare(discount.discountAmount, discountAmount) != 0) {
            return false;
        }
        if (!description.equals(discount.description)) {
            return false;
        }
        return product.equals(discount.product);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = description.hashCode();
        temp = Double.doubleToLongBits(discountAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + product.hashCode();
        return result;
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
