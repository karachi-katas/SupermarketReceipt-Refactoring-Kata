package dojo.supermarket.model;

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
                Discount discount = null;

                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    int numberOfXs = quantityAsInt / 3;
                    double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(p, "3 for 2", -discountAmount);
                } else if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                    discount = handleDiscountXForAmount(p, quantity, offer, unitPrice, quantityAsInt, 2);

                } if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    discount = handleDiscountXForAmount(p, quantity, offer, unitPrice, quantityAsInt, 5);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }

                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }


    private Discount handleDiscountXForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int discountedItemFactor) {
        Discount discount;
        double total = offer.argument * (quantityAsInt / discountedItemFactor) + quantityAsInt % discountedItemFactor * unitPrice;
        double discountTotal = unitPrice * quantity - total;
        discount = new Discount(p, discountedItemFactor + " for " + offer.argument, -discountTotal);
        return discount;
    }


    public void handleBundle(Receipt receipt, Map<Bundle, Integer> bundles, SupermarketCatalog catalog) {
        for (Bundle bundle: bundles.keySet()) {

            boolean canBundleDiscountBeApplied = true;
            for (ProductQuantity productQuantity: bundle.bundleProducts) {
                if (productQuantity.getQuantity() != productQuantities.get(productQuantity.getProduct())) {
                    canBundleDiscountBeApplied = false;
                }
            }

            if (canBundleDiscountBeApplied) {
                int discountPercentage = bundles.get(bundle);


            }

        }
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);


        }

    }
}
