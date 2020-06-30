package offer;

import dojo.supermarket.model.SupermarketTest;
import dojo.supermarket.offer.Offer;
import dojo.supermarket.offer.OfferBuilder;
import java.util.Arrays;

public class OfferFixtures {

    public static final Offer TEN_PERCENT_OFF_TOOTHBRUSH = new OfferBuilder(
            SupermarketTest.TOOTHBRUSH)
            .create(10);
    public static final Offer TEN_PERCENT_OFF_RICE = new OfferBuilder(SupermarketTest.RICE).create(10);
    public static final Offer TWENTY_PERCENT_OFF_APPLES = new OfferBuilder(SupermarketTest.APPLES)
            .create(20);
    public static final Offer TEN_PERCENT_OFF_TOOTHPASTE = new OfferBuilder(
            SupermarketTest.TOOTHPASTE)
            .create(10);
    public static final Offer BUY_2_GET_1_FREE_TOOTHBRUSH = new OfferBuilder(
            SupermarketTest.TOOTHBRUSH)
            .create(2, 1);
    public static final Offer TWO_FOR_099_TOOTHBRUSH = new OfferBuilder(
            SupermarketTest.TOOTHBRUSH)
            .create(0.99, 2);
    public static final Offer FIVE_FOR_749_TOOTHPASTE = new OfferBuilder(
            SupermarketTest.TOOTHPASTE)
            .create(7.49, 5);
    public static final Offer TWO_FOR_099_CHERRY_TOMATOES = new OfferBuilder(
            SupermarketTest.CHERRY_TOMATOES)
            .create(0.99, 2);
    public static final Offer BUNDLE_TOOTHBRUSH_AND_PASTE_WITH_10_PERCENT_DISCOUNT = new OfferBuilder(
            SupermarketTest.TOOTHBRUSH)
            .create(SupermarketTest.TOOTHPASTE, 10);
    public static final Offer BUNDLE_TOOTHBRUSH_AND_PASTE_AND_RICE_WITH_10_PERCENT_DISCOUNT = new OfferBuilder(
            SupermarketTest.TOOTHBRUSH)
            .create(Arrays.asList(SupermarketTest.TOOTHPASTE, SupermarketTest.RICE), 10);
}
