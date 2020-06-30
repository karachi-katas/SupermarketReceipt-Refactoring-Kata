package dojo.supermarket.model;

import dojo.supermarket.offer.Offer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    Map<Product, Double> productQuantities = new LinkedHashMap<>();

    void addItems(Product... products) {
        for (Product product : products) {
            this.addItemQuantity(product, 1.0);
        }
    }

    public void addItemQuantity(Product product, double quantity) {
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    public Map<Product, Double> getProductQuantities() {
        return productQuantities;
    }

    public double getQuantity(Product product) {
        return productQuantities.get(product);
    }

    public int getQuantityAsInt(Product product) {
        return productQuantities.get(product).intValue();
    }

    void handleOffers(Receipt receipt, SupermarketCatalog catalog,
            List<Offer> offerList) {

        for (Product product : productQuantities.keySet()) {
            Offer offer = getOfferFor(product, offerList);

            Discount discount = offer.getDiscount(catalog, product, this);
            if (discount != null) {
                receipt.addDiscount(discount);
            }
        }
    }

    private Offer getOfferFor(Product product, List<Offer> offers) {
        for (Offer offer : offers) {
            if (offer.applicableOn(product)) {
                return offer;
            }
        }
        return Offer.NONE;
    }

    void addProductsToReceipt(Receipt receipt,
            SupermarketCatalog catalog) {
        for (Product product : productQuantities.keySet()) {
            double quantity = getQuantity(product);
            double unitPrice = catalog.getUnitPrice(product);
            double price = quantity * unitPrice;
            receipt.addProduct(product, quantity, unitPrice, price);
        }
    }
}
