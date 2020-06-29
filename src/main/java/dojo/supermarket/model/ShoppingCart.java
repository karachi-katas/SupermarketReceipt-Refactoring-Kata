package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, List<Offer> offers, SupermarketCatalog catalog) {
        OfferHandler offerHandler = new OfferHandler(receipt, catalog);
        for (Offer offer : offers) {
            offerHandler.handle(offer);
        }
    }

    class OfferHandler {
        private Map<Product, Double> quantityLeft;
        private Receipt receipt;
        private SupermarketCatalog catalog;

        public OfferHandler(Receipt receipt, SupermarketCatalog catalog) {
            this.quantityLeft = productQuantities.keySet().stream().collect(Collectors.toMap(Function.identity(), product -> productQuantities.get(product)));
            this.receipt = receipt;
            this.catalog = catalog;
        }

        void handle(Offer offer) {
            List<ProductQuantity> applicableProductQuantities
                = offer.getApplicableProductQuantities(quantityLeft);

            for (ProductQuantity productQuantity : applicableProductQuantities) {
                Product product = productQuantity.getProduct();
                Double applicableQuantity = productQuantity.getQuantity();
                double unitPrice = catalog.getUnitPrice(product);
                quantityLeft.put(product, quantityLeft.get(product) - applicableQuantity);
                offer.getDiscountBuilder().buildFor(product, unitPrice, applicableQuantity)
                    .ifPresent(receipt::addDiscount);
            }
        }
    }
}
