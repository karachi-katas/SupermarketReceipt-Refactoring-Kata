package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import dojo.supermarket.offer.BuyXGetYFree;
import dojo.supermarket.offer.Offer;
import dojo.supermarket.offer.OfferBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import offer.OfferFixtures;
import org.junit.Before;
import org.junit.Test;

public class SupermarketTest {

    public static final Product TOOTHBRUSH = new Product("toothbrush", ProductUnit.Each);
    public static final Product TOOTHPASTE = new Product("toothpaste", ProductUnit.Each);
    public static final Product APPLES = new Product("apples", ProductUnit.Kilo);
    public static final Product RICE = new Product("rice", ProductUnit.Kilo);
    public static final Product CHERRY_TOMATOES = new Product("cherryTomatoes", ProductUnit.Each);

    Teller teller;
    ShoppingCart cart;

    @Before
    public void setupTeller() {
        SupermarketCatalog catalog = new FakeCatalog();
        catalog.addProduct(TOOTHBRUSH, 0.99);
        catalog.addProduct(TOOTHPASTE, 1.79);
        catalog.addProduct(APPLES, 1.99);
        catalog.addProduct(RICE, 2.49);
        catalog.addProduct(CHERRY_TOMATOES, 0.69);

        teller = new Teller(catalog);
        cart = new ShoppingCart();
    }

    @Test
    public void noDiscountTest() {
        teller.addOffers(OfferFixtures.TEN_PERCENT_OFF_TOOTHBRUSH);
        cart.addItemQuantity(APPLES, 2.5);

        // ACT
        Receipt receiptActual = teller.checksOutArticlesFrom(cart);

        // ASSERT
        Receipt receiptExpected = new Receipt();
        receiptExpected.addProduct(APPLES, 2.5, 1.99, 1.99 * 2.5);
        assertEquals(receiptExpected, receiptActual);
    }

    @Test
    public void buyTwoToothBrushesAndGetOneFree() {
        teller.addOffers(OfferFixtures.BUY_2_GET_1_FREE_TOOTHBRUSH);

        cart.addItemQuantity(TOOTHBRUSH, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.98, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void buyFiveToothBrushesAndGetTwoFree() {
        teller.addOffers(OfferFixtures.TWO_FOR_099_TOOTHBRUSH);

        cart.addItemQuantity(TOOTHBRUSH, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(3 * 0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void tenPercentOnRice() {
        teller.addOffers(OfferFixtures.TEN_PERCENT_OFF_RICE);

        cart.addItemQuantity(RICE, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.49 * 0.9, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twentyPercentOnApples() {
        teller.addOffers(OfferFixtures.TWENTY_PERCENT_OFF_APPLES);

        cart.addItemQuantity(APPLES, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.99 * 0.8, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void fiveForAmountForToothpaste() {
        teller.addOffers(OfferFixtures.FIVE_FOR_749_TOOTHPASTE);

        cart.addItemQuantity(TOOTHPASTE, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7.49, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twoBoxesOfCherryTomatoesForDiscountedPrice() {
        teller.addOffers(OfferFixtures.TWO_FOR_099_CHERRY_TOMATOES);

        cart.addItemQuantity(CHERRY_TOMATOES, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void multipleDiscounts() {

        teller.addOffers(OfferFixtures.TWO_FOR_099_CHERRY_TOMATOES, OfferFixtures.TEN_PERCENT_OFF_RICE);

        cart.addItemQuantity(CHERRY_TOMATOES, 3);
        cart.addItemQuantity(RICE, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99 + 0.69 + 2.49 * 0.9, receipt.getTotalPrice(), 0.01);

        List<ReceiptItem> receiptItems = receipt.getItems();

        Set<ReceiptItem> receiptItemsExpected = new HashSet<>();
        receiptItemsExpected.add(new ReceiptItem(CHERRY_TOMATOES, 3, 0.69, 0.69 * 3));
        receiptItemsExpected.add(new ReceiptItem(RICE, 1, 2.49, 2.49));

        Set<ReceiptItem> receiptItemActual = new HashSet<>(receiptItems);

        assertEquals(receiptItemsExpected, receiptItemActual);

        Set<Discount> discountsExpected = new HashSet<>();
        discountsExpected.add(new Discount(CHERRY_TOMATOES, "2 for 0.99", 0.99 - 0.69 * 2));
        discountsExpected.add(new Discount(RICE, "10.0% off", -2.49 * 0.1));

        Set<Discount> discountsActual = new HashSet<>(receipt.getDiscounts());
        assertEquals(discountsExpected, discountsActual);
    }

    @Test
    public void bundledDiscount() {
        teller.addOffers(OfferFixtures.BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79) * 0.9, receipt.getTotalPrice(), 0.01);
    }

//    @Test
//    public void bundledDiscountShouldApplyFirst() {
//        teller.addOffers(
//                OfferFixtures.BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT,
//                OfferFixtures.TEN_PERCENT_OFF_TOOTHPASTE);
//
//        cart.addItems(TOOTHBRUSH, TOOTHPASTE);
//
//        Receipt receipt = teller.checksOutArticlesFrom(cart);
//        assertEquals((0.99 + 1.79) * 0.9, receipt.getTotalPrice(), 0.01);
//    }

    @Test
    public void bundledDiscountAppliesTwice() {
        teller.addOffers(
                OfferFixtures.BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE, TOOTHBRUSH, TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79) * 0.9 * 2, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void bundledDiscountWithMoreThanTwoProducts() {
        teller.addOffers(
                OfferFixtures.BUNDLE_TOOTHBRUSH_AND_PASTE_AND_RICE_WITH_10_PERCENT_DISCOUNT);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE, RICE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79 + 2.49) * 0.9, receipt.getTotalPrice(), 0.01);
    }

    @Test(expected = BundleOfferWithNoBundledProducts.class)
    public void bundledDiscountWithZeroProducts() throws BundleOfferWithNoBundledProducts{
        new OfferBuilder(TOOTHBRUSH).create(Collections.emptyList(), 10.0);
    }

    @Test
    public void bundledDiscountAppliesOnceWithExtraProduct() {
        teller.addOffers(
                OfferFixtures.BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE, TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79) * 0.9 + 1.79, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void bundledDiscountWithOneToothbrushAndTwoPastes() {
        teller.addOffers(
                OfferFixtures.BUNDLE_TOOTHBRUSH_AND_TWO_PASTE_WITH_10_PERCENT_DISCOUNT);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE, TOOTHPASTE, TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79 * 2) * 0.9 + 1.79, receipt.getTotalPrice(), 0.01);
    }
}
