package dojo.supermarket.model;

import dojo.supermarket.model.offers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        Offer offer = null;

        switch (offerType) {
            case ThreeForTwo:
                offer = new ThreeForTwo(product, argument);
                break;

            case TenPercentDiscount:
                offer = new TenPercentDiscount(product, argument);
                break;

            case TwoForAmount:
                offer = new TwoForAmount(product, argument);
                break;

            case FiveForAmount:
                offer = new FiveForAmount(product, argument);
                break;
        }

        Optional.of(offer).ifPresent(o -> this.offers.put(product, o));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
