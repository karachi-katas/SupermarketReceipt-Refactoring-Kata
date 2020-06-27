package dojo.supermarket.model;

import static dojo.supermarket.model.SpecialOfferType.BundledDiscount;
import static dojo.supermarket.model.SpecialOfferType.FiveForAmount;
import static dojo.supermarket.model.SpecialOfferType.PercentDiscount;
import static dojo.supermarket.model.SpecialOfferType.ThreeForTwo;
import static dojo.supermarket.model.SpecialOfferType.TwoForAmount;
import static org.junit.Assert.assertEquals;

import dojo.supermarket.offer.Offer;
import dojo.supermarket.offer.OfferBuilder;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void noDiscountTest() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(PercentDiscount, toothbrush, 10)
                .createOffer();
        teller.addSpecialOffer(offer);

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
    public void buyTwoToothBrushesAndGetOneFree() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(ThreeForTwo, toothbrush, 0.99)
                .createOffer();
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.98, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void buyFiveToothBrushesAndGetTwoFree() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(toothbrush)
                .create(0.99, 2);
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(3 * 0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void tenPercentOnRice() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product rice = new Product("rice", ProductUnit.Kilo);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(PercentDiscount, rice, 10)
                .createOffer();
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(rice, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.49 * 0.9, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twentyPercentOnApples() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product apple = new Product("apple", ProductUnit.Kilo);
        catalog.addProduct(apple, 1.99);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(PercentDiscount, apple, 20)
                .createOffer();
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apple, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.99 * 0.8, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void fiveForAmountForToothpaste() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(toothpaste)
                .create(7.49, 5);
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7.49, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void twoBoxesOfCherryTomatoesForDiscountedPrice() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product cherryTomatoes = new Product("cherryTomatoes", ProductUnit.Each);
        catalog.addProduct(cherryTomatoes, 0.69);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(TwoForAmount, cherryTomatoes, 0.99)
                .createOffer();
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(cherryTomatoes, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99, receipt.getTotalPrice(), 0.01);
    }

    @Test
    public void multipleDiscounts() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product cherryTomatoes = new Product("cherryTomatoes", ProductUnit.Each);
        catalog.addProduct(cherryTomatoes, 0.69);
        Product rice = new Product("rice", ProductUnit.Kilo);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(TwoForAmount, cherryTomatoes, 0.99)
                .createOffer();
        teller.addSpecialOffer(offer);
        Offer otherOffer = new OfferBuilder(PercentDiscount, rice, 10)
                .createOffer();
        teller.addSpecialOffer(otherOffer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(cherryTomatoes, 3);
        cart.addItemQuantity(rice, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99 + 0.69 + 2.49 * 0.9, receipt.getTotalPrice(), 0.01);

        List<ReceiptItem> receiptItems = receipt.getItems();

        ReceiptItem receiptItem = receiptItems.get(0);
        assertEquals(receiptItem.getProduct().getName(), "cherryTomatoes");
        assertEquals(receiptItem.getTotalPrice(), 0.69 * 3, 0.01);
        assertEquals(receiptItem.getPrice(), 0.69, 0.01);

        ReceiptItem receiptItemRice = receiptItems.get(1);
        assertEquals(receiptItemRice.getProduct().getName(), "rice");
        assertEquals(receiptItemRice.getTotalPrice(), 2.49, 0.01);
        assertEquals(receiptItemRice.getPrice(), 2.49, 0.01);

        // TODO: check it out | order changes
//        List<Discount> discounts = receipt.getDiscounts();
//        assertEquals(discounts.get(0).getProduct().getName(), "cherryTomatoes");
//        assertEquals(discounts.get(0).getDiscountAmount(),  0.99 - 0.69 * 2, 0.01);
    }

    @Test
    public void bundledDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);


        Teller teller = new Teller(catalog);
        Offer offer = new OfferBuilder(BundledDiscount, toothbrush, 10)
                .addProduct(toothpaste)
                .createOffer();
        teller.addSpecialOffer(offer);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush);
        cart.addItem(toothpaste);


        Receipt receipt = teller.checksOutArticlesFrom(cart);
        assertEquals((0.99+1.79)*0.9, receipt.getTotalPrice(), 0.01);
    }
}
