package dojo.supermarket.model;

import java.util.Optional;

interface DiscountBuilder {
    Optional<Discount> buildFor(Product product, double unitPrice, double quantity);
}
