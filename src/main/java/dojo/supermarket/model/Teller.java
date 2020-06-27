package dojo.supermarket.model;

import dojo.supermarket.offer.Offer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private List<Offer> offersList = new LinkedList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer offer) {
        offersList.add(offer);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        for (Product product: theCart.productQuantities.keySet()) {
            double quantity = theCart.getQuantity(product);
            double unitPrice = this.catalog.getUnitPrice(product);
            double price = quantity * unitPrice;
            receipt.addProduct(product, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, this.catalog, offersList);

        return receipt;
    }

}
