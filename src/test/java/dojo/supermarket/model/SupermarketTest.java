package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import dojo.supermarket.offer.Offer;
import dojo.supermarket.offer.OfferBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class SupermarketTest {

    public static final Product TOOTHBRUSH = new Product("toothbrush", ProductUnit.Each);
    public static final Product TOOTHPASTE = new Product("toothpaste", ProductUnit.Each);
    public static final Product APPLES = new Product("apples", ProductUnit.Kilo);
    public static final Product RICE = new Product("rice", ProductUnit.Kilo);

    public static final Offer TEN_PERCENT_OFFER_ON_TOOTHBURSH = new OfferBuilder(TOOTHBRUSH)
            .create(10);
    public static final Offer BUY_2_GET_1_FREE_OFFER_ON_TOOTHBRUSH = new OfferBuilder(TOOTHBRUSH)
            .create(2, 1);
    public static final Offer BUY_2_FOR_FIXED_PRICE_OFFER_ON_TOOTHBRUSH = new OfferBuilder(
            TOOTHBRUSH)
            .create(0.99, 2);
    public static final Offer TEN_PERCENT_DISCOUNT_ON_RICE = new OfferBuilder(RICE).create(10);
    public static final Offer TWENTY_PERCENT_DISCOUNT_ON_APPLES = new OfferBuilder(APPLES)
            .create(20);
    public static final Offer FIVE_FOR_FIXED_PRICE_OFFER_ON_TOOTHPASTE = new OfferBuilder(
            TOOTHPASTE)
            .create(7.49, 5);
    public static final Product CHERRY_TOMATOES = new Product("cherryTomatoes", ProductUnit.Each);
    public static final Offer TWO_FOR_DISCOUNTED_PRICE_ON_CHERRY_TOMATOES = new OfferBuilder(
            CHERRY_TOMATOES)
            .create(0.99, 2);

    Teller teller;
    ShoppingCart cart;
    public static final Offer TWO_FOR_099_CHERRY_TOMATOES = new OfferBuilder(CHERRY_TOMATOES)
            .create(0.99, 2);
    public static final Offer TEN_PERCENT_OFF_ON_RICE = new OfferBuilder(RICE).create(10);
    public static final Offer BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT = new OfferBuilder(
            TOOTHBRUSH)
            .create(TOOTHPASTE, 10);
    public static final Offer TEN_PERCENT_OFF_ON_TOOTHPASTE = new OfferBuilder(TOOTHPASTE)
            .create(10);

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
        teller.addSpecialOffer(TEN_PERCENT_OFFER_ON_TOOTHBURSH);
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
        teller.addSpecialOffer(BUY_2_GET_1_FREE_OFFER_ON_TOOTHBRUSH);

        cart.addItemQuantity(TOOTHBRUSH, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.98, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void buyFiveToothBrushesAndGetTwoFree() {
        teller.addSpecialOffer(BUY_2_FOR_FIXED_PRICE_OFFER_ON_TOOTHBRUSH);

        cart.addItemQuantity(TOOTHBRUSH, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(3 * 0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void tenPercentOnRice() {
        teller.addSpecialOffer(TEN_PERCENT_DISCOUNT_ON_RICE);

        cart.addItemQuantity(RICE, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.49 * 0.9, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twentyPercentOnApples() {
        teller.addSpecialOffer(TWENTY_PERCENT_DISCOUNT_ON_APPLES);

        cart.addItemQuantity(APPLES, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.99 * 0.8, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void fiveForAmountForToothpaste() {
        teller.addSpecialOffer(FIVE_FOR_FIXED_PRICE_OFFER_ON_TOOTHPASTE);

        cart.addItemQuantity(TOOTHPASTE, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7.49, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twoBoxesOfCherryTomatoesForDiscountedPrice() {
        teller.addSpecialOffer(TWO_FOR_DISCOUNTED_PRICE_ON_CHERRY_TOMATOES);

        cart.addItemQuantity(CHERRY_TOMATOES, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void multipleDiscounts() {

        teller.addSpecialOffer(TWO_FOR_099_CHERRY_TOMATOES);
        teller.addSpecialOffer(TEN_PERCENT_OFF_ON_RICE);

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
        teller.addSpecialOffer(BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT);

        cart.addItem(TOOTHBRUSH);
        cart.addItem(TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79) * 0.9, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void bundledDiscountShouldApplyFirst() {
        teller.addOffers(
                BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT,
                TEN_PERCENT_OFF_ON_TOOTHPASTE);

        cart.addItems(TOOTHBRUSH, TOOTHPASTE);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99 + 1.79) * 0.9, receipt.getTotalPrice(), 0.01);
    }
}
