package dojo.supermarket.model;

import dojo.supermarket.offers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    ProductQuantities productQuantities = new ProductQuantities();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }



    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        productQuantities.addItemQuantity(product, quantity);
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p : productQuantities.getProducts()) {
            double quantity = productQuantities.getQuantity(p);

            if (!offers.containsKey(p)) {
                continue;
            }

            Offer offer = offers.get(p);
            double unitPrice = catalog.getUnitPrice(p);
            int quantityAsInt = (int) quantity;

            DiscountOffer discountOffer = OfferFactory.getDiscountOffer(offer.offerType, quantityAsInt);
            Discount discount = discountOffer.getDiscount(p, quantity, offer, unitPrice);
            receipt.addDiscount(discount);

        }
    }

}
