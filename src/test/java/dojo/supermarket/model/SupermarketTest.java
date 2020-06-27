package dojo.supermarket.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import org.junit.Test;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void tenPercentDiscount() {
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
    public void ShouldHave3For2DiscountOnToothBrushes(){

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 1);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals("3 for 2", receipt.getDiscounts().get(0).getDescription());
        assertEquals(-1.0,receipt.getDiscounts().get(0).getDiscountAmount(),0.1);

        //assertEquals(3,);
    }

    @Test
    public void ShouldHave20PercentDiscountOnApples(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 100);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 20.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 1);


        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        //receipt.getItems();
        assertEquals("20.0% off", receipt.getDiscounts().get(0).getDescription());
        assertEquals(-20.0,receipt.getDiscounts().get(0).getDiscountAmount(),0.1);
    }

    @Test
    public void ShouldHave10PercentDiscountOnRice(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 100);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, rice, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(rice, 1);


        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        //receipt.getItems();
        assertEquals("10.0% off", receipt.getDiscounts().get(0).getDescription());
        assertEquals(-10.0,receipt.getDiscounts().get(0).getDiscountAmount(),0.1);
    }


    @Test
    public void ShouldHave5ForAmountForToothPaste(){

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals("5 for 7.49", receipt.getDiscounts().get(0).getDescription());
        assertEquals(-1.46,receipt.getDiscounts().get(0).getDiscountAmount(),0.01);

        //assertEquals(3,);
    }

    @Test
    public void ShouldHave5ForAmountFor(){

        SupermarketCatalog catalog = new FakeCatalog();
        Product cherry = new Product("cherry", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals("5 for 7.49", receipt.getDiscounts().get(0).getDescription());
        assertEquals(-1.46,receipt.getDiscounts().get(0).getDiscountAmount(),0.01);

        //assertEquals(3,);
    }


}
