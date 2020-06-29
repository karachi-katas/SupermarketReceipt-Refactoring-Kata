package dojo.supermarket.model;

import java.util.Optional;

class NForMDiscountBuilder implements DiscountBuilder {
    private int n;
    private int m;

    public NForMDiscountBuilder(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public Optional<Discount> buildFor(Product product, double unitPrice, double quantity) {
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt / n;
        if (quantityAsInt > m) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * m * unitPrice) + quantityAsInt % n * unitPrice);
            return Optional.of(new Discount(product, n + " for " + m, -discountAmount));
        }

        return Optional.empty();
    }
}
