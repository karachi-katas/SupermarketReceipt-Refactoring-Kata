package dojo.supermarket.model;

public enum SpecialOfferType {

    ThreeForTwo(3, 2), TenPercentDiscount(), TwoForAmount(2), FiveForAmount(5);

    public final int sourceQuantity;
    public final int targetQuantity;


    SpecialOfferType() {
        this.sourceQuantity = 1;
        this.targetQuantity = 1;
    }

    SpecialOfferType(int quantity) {
        this.sourceQuantity = quantity;
        this.targetQuantity = quantity;
    }

    SpecialOfferType(int sourceQuantity, int targetQuantity) {
        this.sourceQuantity = sourceQuantity;
        this.targetQuantity = targetQuantity;
    }

}
