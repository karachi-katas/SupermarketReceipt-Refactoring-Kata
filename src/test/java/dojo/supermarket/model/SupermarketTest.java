package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import dojo.supermarket.ReceiptPrinter;
import org.junit.Test;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void shouldNotApplyDiscountWhenNoDiscountedItemInCart() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String result = new ReceiptPrinter().printReceipt(receipt);
        System.out.println(result);

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
    public void shouldApplyTenPercentDiscountOnToothbrushInCart() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String result = new ReceiptPrinter().printReceipt(receipt);
        System.out.println(result);

        // ASSERT
        assertEquals(1.78, receipt.getTotalPrice(), 0.01);
        assertEquals(1, receipt.getDiscounts().size());
        assertEquals(-0.2, receipt.getDiscounts().get(0).getDiscountAmount(),0.01);
        assertEquals(toothbrush, receipt.getDiscounts().get(0).getProduct());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem.getProduct());
        assertEquals(0.99, receiptItem.getPrice(), 0.01);
        assertEquals(2*0.99, receiptItem.getTotalPrice(), 0.01);
        assertEquals(2, receiptItem.getQuantity(), 0.01);

    }

    @Test
    public void shouldApplyFiveForAmountDiscountOnToothpasteInCart() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 15);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String result = new ReceiptPrinter().printReceipt(receipt);
        System.out.println(result);

        // ASSERT
        assertEquals(22.47, receipt.getTotalPrice(), 0.01);
        assertEquals(1, receipt.getDiscounts().size());
        assertEquals(-4.38, receipt.getDiscounts().get(0).getDiscountAmount(),0.01);
        assertEquals(toothpaste, receipt.getDiscounts().get(0).getProduct());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(toothpaste, receiptItem.getProduct());
        assertEquals(1.79, receiptItem.getPrice(), 0.01);
        assertEquals(15*1.79, receiptItem.getTotalPrice(), 0.01);
        assertEquals(15, receiptItem.getQuantity(), 0.01);

    }

    @Test
    public void shouldApplyTwoForAmountDiscountOnItemInCart() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 0.69);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothpaste, 0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String result = new ReceiptPrinter().printReceipt(receipt);
        System.out.println(result);

        // ASSERT
        assertEquals(1.68, receipt.getTotalPrice(), 0.01);
        assertEquals(1, receipt.getDiscounts().size());
        assertEquals(-0.39, receipt.getDiscounts().get(0).getDiscountAmount(),0.01);
        assertEquals(toothpaste, receipt.getDiscounts().get(0).getProduct());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(toothpaste, receiptItem.getProduct());
        assertEquals(0.69, receiptItem.getPrice(), 0.01);
        assertEquals(3*0.69, receiptItem.getTotalPrice(), 0.01);
        assertEquals(3, receiptItem.getQuantity(), 0.01);

    }
}
