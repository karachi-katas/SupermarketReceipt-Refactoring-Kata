package dojo.supermarket.discounts;

import dojo.supermarket.model.Product;

public class ThreeForTwo extends DiscountInstance {

    ThreeForTwo(Product p, double quantity, double unitPrice) {
        int quantityAsInt = (int)quantity;
        int numberOfXs = (quantityAsInt / 3);

        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        this.product = p;
        this.description = "3 for 2";
        this.discountAmount = -discountAmount;
    }
}
