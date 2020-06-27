package dojo.supermarket.model;

import dojo.supermarket.discounts.Discount;
import dojo.supermarket.discounts.DiscountFactory;

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
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;

                int x = 1;
                if (offer.offerType == SpecialOfferType.ThreeForTwo) {
                    x = 3;
                } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
                    x = 2;
                } if (offer.offerType == SpecialOfferType.FiveForAmount) {
                    x = 5;
                }

                int numberOfXs = quantityAsInt / x;

                Discount discount = DiscountFactory.getInstance(offer.offerType, p, quantity, offer, unitPrice, quantityAsInt, x, numberOfXs);

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
