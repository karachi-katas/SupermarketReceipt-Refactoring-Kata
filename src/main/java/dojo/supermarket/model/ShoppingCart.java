package dojo.supermarket.model;

import dojo.supermarket.strategy.DiscountFactory;
import dojo.supermarket.strategy.SpecialOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        double previousQuantity = isExistingProduct(product) ? productQuantities.get(product) : 0;

        productQuantities.put(product, previousQuantity + quantity);
    }

    private boolean isExistingProduct(Product product) {
        return productQuantities.containsKey(product);
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);

                SpecialOffer specialOffer = DiscountFactory.getSpecialOffer(offer.offerType);
                Discount discount = specialOffer.getDiscount(p, quantity, offer, unitPrice);

                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }
}
