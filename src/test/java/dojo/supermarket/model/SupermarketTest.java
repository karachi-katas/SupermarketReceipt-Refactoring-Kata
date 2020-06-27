package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void tenPercentDiscount() {
        // SETUP
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new Offer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice(), 0.01);
        assertEquals(2.5*1.99, receiptItem.getTotalPrice(), 0.01);
        assertEquals(2.5, receiptItem.getQuantity(), 0.01);

    }

    @Test
    public void testTwoForAmountOfferOnTomatoes() {
        // SETUP
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        Product tomatoes = new Product("cherry tomatoes", ProductUnit.Each);
        catalog.addProduct(tomatoes, 0.69);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new Offer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0));
        teller.addSpecialOffer(new Offer(SpecialOfferType.TwoForAmount, tomatoes, 0.99));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        cart.addItemQuantity(tomatoes, 2);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(5.965, receipt.getTotalPrice(), 0.01);
        assertEquals(Arrays.asList(new Discount(tomatoes, "2 for 0.99", 0.99 - (catalog.getUnitPrice(tomatoes) * 2.0))), receipt.getDiscounts());
        assertEquals(2, receipt.getItems().size());

        ReceiptItem expectedItem1 = new ReceiptItem(apples, 2.5, 1.99);
        ReceiptItem expectedItem2 = new ReceiptItem(tomatoes, 2.0, 0.69);

        Assert.assertEquals(Arrays.asList(expectedItem1, expectedItem2), receipt.getItems());
    }
}
