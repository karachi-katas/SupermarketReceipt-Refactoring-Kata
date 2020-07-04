package dojo.supermarket.model;

public enum SpecialOfferType {

    ThreeForTwo(3), TenPercentDiscount(), TwoForAmount(2), FiveForAmount(5);

    public final int value;

    SpecialOfferType() {
        this.value = 1;
    }

    SpecialOfferType(int value) {
        this.value = value;
    }

}
