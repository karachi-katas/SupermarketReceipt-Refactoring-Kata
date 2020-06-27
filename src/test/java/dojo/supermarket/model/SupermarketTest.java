package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import dojo.supermarket.discounts.Discount;
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

    @Test
    public void testThreeForTwoOnToothbrushes() {
        // SETUP
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        Product tomatoes = new Product("cherry tomatoes", ProductUnit.Each);
        catalog.addProduct(tomatoes, 0.69);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new Offer(SpecialOfferType.ThreeForTwo, toothbrush, 0.0));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(1.98, receipt.getTotalPrice(), 0.01);
        assertEquals(Arrays.asList(new Discount(toothbrush, "3 for 2", (1.98 - (catalog.getUnitPrice(toothbrush) * 3.0)))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());

        ReceiptItem expectedItem1 = new ReceiptItem(toothbrush, 3, 0.99);

        Assert.assertEquals(Arrays.asList(expectedItem1), receipt.getItems());
    }

    @Test
    public void testFor10PercentOffOnRice() {
        // SETUP
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        Product tomatoes = new Product("cherry tomatoes", ProductUnit.Each);
        catalog.addProduct(tomatoes, 0.69);
        Product rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new Offer(SpecialOfferType.TenPercentDiscount, rice, 10));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(rice, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(2.241, receipt.getTotalPrice(), 0.01);
        assertEquals(Arrays.asList(new Discount(rice, "10.0% off", (-(catalog.getUnitPrice(rice) * 0.1)))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());

        ReceiptItem expectedItem1 = new ReceiptItem(rice, 1, 2.49);

        Assert.assertEquals(Arrays.asList(expectedItem1), receipt.getItems());
    }

    @Test
    public void testFiveForAmount() {
        // SETUP
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        Product tomatoes = new Product("cherry tomatoes", ProductUnit.Each);
        catalog.addProduct(tomatoes, 0.69);
        Product rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new Offer(SpecialOfferType.FiveForAmount, toothpaste, 7.49));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(7.49, receipt.getTotalPrice(), 0.01);
        assertEquals(Arrays.asList(new Discount(toothpaste, "5 for 7.49", (7.49 - catalog.getUnitPrice(toothpaste) * 5.0))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());

        ReceiptItem expectedItem1 = new ReceiptItem(toothpaste, 5, catalog.getUnitPrice(toothpaste));

        Assert.assertEquals(Arrays.asList(expectedItem1), receipt.getItems());
    }
}
