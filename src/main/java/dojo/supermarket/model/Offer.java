package dojo.supermarket.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    Optional<List<ProductQuantity>> getApplicableProductQuantities(Map<Product, Double> productQuantities) {
        int countOfProductsMatchingOfferProduccts = 0;
        double applicableQuantity = -1;
        for (Product product : productQuantities.keySet()) {
            if (this.products.contains(product)) {
                ++countOfProductsMatchingOfferProduccts;
                if (applicableQuantity == -1 || applicableQuantity > productQuantities.get(product)) {
                    applicableQuantity = productQuantities.get(product);
                }
                if (countOfProductsMatchingOfferProduccts == this.products.size()) {
                    break;
                }
            }
        }

        if (countOfProductsMatchingOfferProduccts == this.products.size() && applicableQuantity > 0) {
            return Optional.of(createProductQuantitiesMap(applicableQuantity));
        }

        return Optional.empty();
    }

    private List<ProductQuantity> createProductQuantitiesMap(double quantity) {
        return this.products.stream()
            .map(product -> new ProductQuantity(product, quantity))
            .collect(Collectors.toList());
    }
}
