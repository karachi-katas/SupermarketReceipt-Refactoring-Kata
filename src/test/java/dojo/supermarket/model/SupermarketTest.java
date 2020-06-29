package dojo.supermarket.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void tenPercentDiscount1() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 0.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(apples, receiptItem1.getProduct());
        assertEquals(1.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(2.5*1.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(2.5, receiptItem1.getQuantity(), 0.0001);

    }

    @Test
    public void tenPercentDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 0.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.4775, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(apples, "10.0% off", -0.4975)), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(apples, receiptItem1.getProduct());
        assertEquals(1.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(2.5*1.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(2.5, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void threeForTwo() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(1.98, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "3 for 2", -(0.99 * 3 - 0.99 * 2))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(3*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(3, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void twoForAmountForTwo() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.5);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(1.5, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "2 for 1.5", -(0.99 * 2 - 1.5))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(2*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(2, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void twoForAmountForThree() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.5);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(2.49, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "2 for 1.5", -(0.99 * 3 - 2.49))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(3*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(3, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void twoForAmountForFour() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.5);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 4);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(3.0, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "2 for 1.5", -(0.99 * 4 - 1.5 * 2))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(4*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(4, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void fiveForAmountForFour() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 4.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 4);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4*0.99, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(4*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(4, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void fiveForAmountForFive() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 4.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 5);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.0, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "5 for 4.0", -(0.99 * 5 - 4.0))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(5*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(5, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void fiveForAmountForSix() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 4.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 6);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.99, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.singletonList(new Discount(toothbrush, "5 for 4.0", -(0.99 * 6 - 4.99))), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.99, receiptItem1.getPrice(), 0.0001);
        assertEquals(6*0.99, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(6, receiptItem1.getQuantity(), 0.0001);
    }

    @Test
    public void bundleOfferNotApplying() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apple = new Product("apple", ProductUnit.Each);

        catalog.addProduct(toothbrush, 0.50);
        catalog.addProduct(toothpaste, 0.40);
        catalog.addProduct(apple, 0.40);

        Teller teller = new Teller(catalog);
        teller.addBundleOffer(Arrays.asList(toothbrush, toothpaste), 5.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 1);
        cart.addItemQuantity(apple, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(0.90, receipt.getTotalPrice(), 0.0001);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());

        assertEquals(2, receipt.getItems().size());

        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.50, receiptItem1.getPrice(), 0.0001);
        assertEquals(1*0.50, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(1, receiptItem1.getQuantity(), 0.0001);

        ReceiptItem receiptItem2 = receipt.getItems().get(1);
        assertEquals(apple, receiptItem2.getProduct());
        assertEquals(0.40, receiptItem2.getPrice(), 0.0001);
        assertEquals(1*0.40, receiptItem2.getTotalPrice(), 0.0001);
        assertEquals(1, receiptItem2.getQuantity(), 0.0001);
    }

    @Test
    public void bundleOffer() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);

        catalog.addProduct(toothbrush, 0.50);
        catalog.addProduct(toothpaste, 0.40);

        Teller teller = new Teller(catalog);
        teller.addBundleOffer(Arrays.asList(toothbrush, toothpaste), 5.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 1);
        cart.addItemQuantity(toothpaste, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(0.855, receipt.getTotalPrice(), 0.0001);
        assertEquals(Arrays.asList(
            new Discount(toothbrush, "bundle discount - 5.0% off", -(1*0.025)),
            new Discount(toothpaste, "bundle discount - 5.0% off", -(1*0.020))
        ), receipt.getDiscounts());

        assertEquals(2, receipt.getItems().size());

        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.50, receiptItem1.getPrice(), 0.0001);
        assertEquals(1*0.50, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(1, receiptItem1.getQuantity(), 0.0001);

        ReceiptItem receiptItem2 = receipt.getItems().get(1);
        assertEquals(toothpaste, receiptItem2.getProduct());
        assertEquals(0.40, receiptItem2.getPrice(), 0.0001);
        assertEquals(1*0.40, receiptItem2.getTotalPrice(), 0.0001);
        assertEquals(1, receiptItem2.getQuantity(), 0.0001);
    }

    @Test
    public void bundleOfferForMultipleBundle() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);

        catalog.addProduct(toothbrush, 0.50);
        catalog.addProduct(toothpaste, 0.40);

        Teller teller = new Teller(catalog);
        teller.addBundleOffer(Arrays.asList(toothbrush, toothpaste), 5.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(toothpaste, 2);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(1.710, receipt.getTotalPrice(), 0.0001);
        assertEquals(Arrays.asList(
            new Discount(toothbrush, "bundle discount - 5.0% off", -(2*0.025)),
            new Discount(toothpaste, "bundle discount - 5.0% off", -(2*0.020))
        ), receipt.getDiscounts());

        assertEquals(2, receipt.getItems().size());

        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.50, receiptItem1.getPrice(), 0.0001);
        assertEquals(2*0.50, receiptItem1.getTotalPrice(), 0.0001);
        assertEquals(2, receiptItem1.getQuantity(), 0.0001);

        ReceiptItem receiptItem2 = receipt.getItems().get(1);
        assertEquals(toothpaste, receiptItem2.getProduct());
        assertEquals(0.40, receiptItem2.getPrice(), 0.0001);
        assertEquals(2*0.40, receiptItem2.getTotalPrice(), 0.0001);
        assertEquals(2, receiptItem2.getQuantity(), 0.0001);
    }

    @Test
    public void bundleOfferForPartialBundles() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);

        catalog.addProduct(toothbrush, 0.50);
        catalog.addProduct(toothpaste, 0.40);

        Teller teller = new Teller(catalog);
        teller.addBundleOffer(Arrays.asList(toothbrush, toothpaste), 5.0);
        teller.addBundleOffer(Arrays.asList(toothbrush), 50.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(toothpaste, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(1.105, receipt.getTotalPrice(), 0.0001);
        assertEquals(Arrays.asList(
            new Discount(toothbrush, "bundle discount - 5.0% off", -(1*0.025)),
            new Discount(toothpaste, "bundle discount - 5.0% off", -(1*0.020)),
            new Discount(toothbrush, "bundle discount - 50.0% off", -(1*0.25))
        ), receipt.getDiscounts());

        assertEquals(2, receipt.getItems().size());

        ReceiptItem receiptItem1 = receipt.getItems().get(0);
        assertEquals(toothbrush, receiptItem1.getProduct());
        assertEquals(0.50, receiptItem1.getPrice(), 0.0001);
        assertEquals(2*0.50, receiptItem1.getTotalPrice(), receipt.getTotalPrice());
        assertEquals(2, receiptItem1.getQuantity(), 0.0001);

        ReceiptItem receiptItem2 = receipt.getItems().get(1);
        assertEquals(toothpaste, receiptItem2.getProduct());
        assertEquals(0.40, receiptItem2.getPrice(), 0.0001);
        assertEquals(1*0.40, receiptItem2.getTotalPrice(), 0.0001);
        assertEquals(1, receiptItem2.getQuantity(), 0.0001);
    }
}
