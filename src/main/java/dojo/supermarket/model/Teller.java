package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teller {

    private final SupermarketCatalog catalog;
    private List<Offer> offers = new ArrayList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        DiscountBuilder discountBuilder = createDiscountBuilder(offerType, argument);
        this.offers.add(new Offer(Arrays.asList(product), discountBuilder));
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

//        theCart.handleOffers(receipt, this.offers, this.catalog);
        theCart.handleOffers(receipt, this.offers, this.catalog);
        return receipt;
    }

    public void addBundleOffer(List<Product> products, double discountPercentage) {
        DiscountBuilder discountBuilder = new PercentDiscountBuilder(
            discountPercentage,
            description -> "bundle discount - " + description);
        offers.add(new Offer(products, discountBuilder));
    }

    private static DiscountBuilder createDiscountBuilder(SpecialOfferType offerType, double argument) {
        switch (offerType) {
            case ThreeForTwo:
                return new NForMDiscountBuilder(3, 2);
            case TwoForAmount:
                return new ItemsForAmountDiscountBuilder(argument, 2);
            case FiveForAmount:
                return new ItemsForAmountDiscountBuilder(argument, 5);
            case TenPercentDiscount:
                return new PercentDiscountBuilder(10.0);
            default:
                throw new IllegalArgumentException();
        }
    }
}
