package dojo.supermarket.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {
    @Test
    public void testAddItemForSingleItem() {
        Product product = new Product("abc", ProductUnit.Kilo);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(product);

        List<ProductQuantity> items = shoppingCart.getItems();
        assertEquals(1, items.size());
        assertProductQuantity(product, 1.0, items.get(0));
        assertEquals(1.0, shoppingCart.productQuantities().get(product), 0.00000001);
    }

    @Test
    public void testAddItemForSingleItemMultipleTimes() {
        Product product = new Product("abc", ProductUnit.Kilo);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(product);
        shoppingCart.addItem(product);

        List<ProductQuantity> items = shoppingCart.getItems();
        assertEquals(2, items.size());
        assertProductQuantity(product, 1.0, items.get(0));
        assertProductQuantity(product, 1.0, items.get(1));
        assertEquals(2.0, shoppingCart.productQuantities().get(product), 0.00000001);
    }

    private void assertProductQuantity(Product product, double quantity, ProductQuantity productQuantity) {
        assertEquals(product, productQuantity.getProduct());
        assertEquals(quantity, productQuantity.getQuantity(), 0.0000000001);
    }
    @Test
    public void testAddItemForMultipleItemMultipleTimes() {
        Product product1 = new Product("abc", ProductUnit.Kilo);
        Product product2 = new Product("xyz", ProductUnit.Kilo);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(product1);
        shoppingCart.addItem(product2);
        shoppingCart.addItem(product1);
        shoppingCart.addItem(product2);
        shoppingCart.addItem(product1);

        List<ProductQuantity> items = shoppingCart.getItems();
        assertEquals(5, items.size());
        assertProductQuantity(product1, 1.0, items.get(0));
        assertProductQuantity(product2, 1.0, items.get(1));
        assertProductQuantity(product1, 1.0, items.get(2));
        assertProductQuantity(product2, 1.0, items.get(3));
        assertProductQuantity(product1, 1.0, items.get(4));
        assertEquals(3.0, shoppingCart.productQuantities().get(product1), 0.00000001);
        assertEquals(2.0, shoppingCart.productQuantities().get(product2), 0.00000001);
    }
}
