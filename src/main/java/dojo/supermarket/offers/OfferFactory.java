package dojo.supermarket.offers;

import dojo.supermarket.model.SpecialOfferType;

import java.util.ArrayList;
import java.util.List;

public class OfferFactory {
    private static List<DiscountOffer> offerTypes=new ArrayList<DiscountOffer>(){
        {
            add(new TwoForAmountOffer());
            add(new ThreeForTwo());
            add(new FiveForAmount());
            add(new PercentDiscount());
        }
    };

    public static DiscountOffer getDiscountOffer(SpecialOfferType offerType, int quantityAsInt) {

        return offerTypes.stream()
                .filter(shoppingCartOffer->shoppingCartOffer.isApplicable(offerType,quantityAsInt))
                .findFirst()
                .orElse(new NoDiscount());
    }

}
