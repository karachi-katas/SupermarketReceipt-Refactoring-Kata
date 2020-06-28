package dojo.supermarket.model;

import dojo.supermarket.offer.Offer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Teller {

    private final SupermarketCatalog catalog;
    private List<Offer> offersList = new LinkedList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addOffers(Offer... offer) {
        offersList.addAll(Arrays.asList(offer));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        theCart.addProductsToReceipt(receipt, this.catalog);
        theCart.handleOffers(receipt, this.catalog, offersList);

        return receipt;
    }

}
