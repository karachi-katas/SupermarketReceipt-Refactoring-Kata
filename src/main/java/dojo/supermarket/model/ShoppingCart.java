package dojo.supermarket.model;

import dojo.supermarket.discount.Discount;
import dojo.supermarket.discount.DiscountCalculator;
import dojo.supermarket.discount.FiveForAmount;
import dojo.supermarket.discount.TenPercent;
import dojo.supermarket.discount.ThreeForTwo;
import dojo.supermarket.discount.TwoForAmount;
import dojo.supermarket.type.SpecialOfferType;

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

    public Discount getCartDiscount(Map<Product, Offer> offers, SupermarketCatalog catalog) {
        Discount discount = null;
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {

                Offer offer = offers.get(p);

                int quantityAsInt = (int) quantity;

                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    DiscountCalculator discountThreeForTwo = new ThreeForTwo();
                    return discountThreeForTwo.calculateDiscount(offer,catalog,p,quantity);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    DiscountCalculator tenPercent = new TenPercent();
                    return tenPercent.calculateDiscount(offer,catalog,p,quantity);
                }

                if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    DiscountCalculator fiveForAmount = new FiveForAmount();
                    return fiveForAmount.calculateDiscount(offer,catalog,p,quantity);
                }
                if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                    DiscountCalculator twoForAmount = new TwoForAmount();
                    return twoForAmount.calculateDiscount(offer,catalog,p,quantity);
                }

            }
        }
        return discount;

    }
}

