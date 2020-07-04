package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    protected double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    protected Discount getDiscount(Product p, double quantity, double unitPrice) {
        return new Discount(p, argument + "% off", -quantity * unitPrice * argument / 100.0);
    }

    void applyDiscount(Receipt receipt, SupermarketCatalog catalog,
                       double quantity) {
        Product product = getProduct();
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            discount = getDiscount(product, quantity, unitPrice);
        }
        else {
            if (offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                int x = 3;
                int numberOfXs = quantityAsInt / x;
                double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                discount = new Discount(product, "3 for 2", -discountAmount);
            }
            if (offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                int x = 2;
                int numberOfXs = quantityAsInt / x;
                double total = argument * numberOfXs + quantityAsInt % 2 * unitPrice;
                double discountN = unitPrice * quantity - total;
                discount = new Discount(product, "2 for " + argument, -discountN);
            }
            if (offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                int x = 5;
                int numberOfXs = quantityAsInt / x;
                double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                discount = new Discount(product, x + " for " + argument, -discountTotal);
            }
        }
        if (discount != null)
            receipt.addDiscount(discount);
    }
}
