package dojo.supermarket.model;

import java.util.Optional;

class PercentDiscountBuilder implements DiscountBuilder {
    private double percentage;
    private DiscountDescriptionDecorator descriptionDecorator;

    public PercentDiscountBuilder(double percentage) {
        this.percentage = percentage;
    }

    public PercentDiscountBuilder(double percentage, DiscountDescriptionDecorator descriptionDecorator) {
        this.percentage = percentage;
        this.descriptionDecorator = descriptionDecorator;
    }

    public Optional<Discount> buildFor(Product product, double unitPrice, double quantity) {
        String description = this.percentage + "% off";
        return Optional.of(new Discount(
            product,
            descriptionDecorator != null ? descriptionDecorator.decorate(description) : description,
            -quantity * unitPrice * this.percentage / 100.0));
    }
}
