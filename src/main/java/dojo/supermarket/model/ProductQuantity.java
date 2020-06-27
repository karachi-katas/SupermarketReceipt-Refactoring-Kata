package dojo.supermarket.model;

public class ProductQuantity {
    private final Product product;
    private final double quantity;
    private boolean isBundleApplied = false;

    public ProductQuantity(Product product, double weight) {
        this.product = product;
        this.quantity = weight;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }
}
