package dojo.supermarket.model;

import dojo.supermarket.offer.Offer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer offer) {
        this.offers.put(offer.getProduct(), offer);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        for (Product product: theCart.productQuantities.keySet()) {
            double quantity = theCart.getQuantity(product);
            double unitPrice = this.catalog.getUnitPrice(product);
            double price = quantity * unitPrice;
            receipt.addProduct(product, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
