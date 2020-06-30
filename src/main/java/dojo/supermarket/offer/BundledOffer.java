package dojo.supermarket.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ShoppingCart;
import dojo.supermarket.model.SupermarketCatalog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BundledOffer extends Offer {

    List<Product> bundledProducts;
    Map<Product, Integer> bundledProducts;

    double discountPercentage;

    public BundledOffer(Product product, Product bundledProduct,
            double discountPercentage) {
        super(product);
        this.bundledProducts = new HashMap<>();
        bundledProducts.put(bundledProduct, 1);
        this.discountPercentage = discountPercentage;
    }

    public BundledOffer(Product product, List<Product> bundledProducts, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
        this.bundledProducts = bundledProducts.stream().collect(
                Collectors.toMap(product1 -> product1, product1 -> 1));
    }

    public BundledOffer(Product product, Map<Product, Integer> bundledProducts,
            double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
        this.bundledProducts = bundledProducts;
    }

    @Override
    public Discount getDiscount(SupermarketCatalog catalog, Product product,
            ShoppingCart shoppingCart) {

        int minQuantity = shoppingCart.getQuantityAsInt(product);

        for (Entry<Product, Integer> bundledProductQuantity: bundledProducts.entrySet()) {

            int quantity = shoppingCart.getQuantityAsInt(bundledProductQuantity.getKey());

            minQuantity = Math.min(minQuantity, quantity / bundledProductQuantity.getValue());
        }

        minQuantity = Math.min(minQuantity, shoppingCart.getQuantityAsInt(product));

        if (minQuantity > 0) {
            double sum = bundledProducts.stream().mapToDouble(catalog::getUnitPrice).sum();
            System.out.println(sum);
            double unitPriceForProduct = catalog.getUnitPrice(product);
            double discountPrice =
                    minQuantity * (unitPriceForProduct + sum) * discountPercentage / 100;
            return new Discount(product, "", -discountPrice);
        }
        return null;
    }
}
