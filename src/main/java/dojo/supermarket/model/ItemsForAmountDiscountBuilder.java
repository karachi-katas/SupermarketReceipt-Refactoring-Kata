package dojo.supermarket.model;

import java.util.Optional;

class ItemsForAmountDiscountBuilder implements DiscountBuilder {
    private double amount;
    private int n;

    public ItemsForAmountDiscountBuilder(double amount, int n) {
        this.amount = amount;
        this.n = n;
    }

    @Override
    public Optional<Discount> buildFor(Product product, double unitPrice, double quantity) {
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / n;
        if (quantityAsInt >= n) {
            double discountTotal = unitPrice * quantity - (this.amount * numberOfXs + quantityAsInt % n * unitPrice);
            return Optional.of(new Discount(product,  n+" for " + this.amount, -discountTotal));
        }

        return Optional.empty();
    }
}
