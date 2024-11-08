package store.purchase;

import java.util.List;

public class Payment {
    private int totalPurchaseAmount;
    private int promotionDiscountAmount;
    private int membershipDiscountAmount;
    private int finalPaymentAmount;

    public Payment(List<Item> purchaseItems, List<Item> giftItems) {
        validate(purchaseItems, giftItems);
        initialize(purchaseItems, giftItems);
    }

    private void validate(List<Item> purchaseItems, List<Item> giftItems) {
        if (purchaseItems == null) {
            throw new IllegalArgumentException("[ERROR] 구매 물품은 null이 될 수 없습니다.");
        }

        if (giftItems == null) {
            throw new IllegalArgumentException("[ERROR] 구매 물품은 null이 될 수 없습니다.");
        }
    }

    private void initialize(List<Item> purchaseItems, List<Item> giftItems) {
        this.totalPurchaseAmount = calculateTotalPurchaseAmount(purchaseItems);
        this.promotionDiscountAmount = calculatePromotionDiscountAmount(giftItems);
        this.membershipDiscountAmount = calculateMembershipDiscountAmount(totalPurchaseAmount, promotionDiscountAmount);
        this.finalPaymentAmount = calculateFinalPaymentAmount(totalPurchaseAmount, promotionDiscountAmount, membershipDiscountAmount);
    }

    private int calculateTotalPurchaseAmount(List<Item> purchaseItems) {
        return purchaseItems.stream().mapToInt(Item::getAmount).sum();
    }

    private int calculatePromotionDiscountAmount(List<Item> giftItems) {
        return giftItems.stream().mapToInt(Item::getDiscountAmount).sum();
    }

    private int calculateMembershipDiscountAmount(int totalPurchaseAmount, int promotionDiscountAmount) {
        return (totalPurchaseAmount - promotionDiscountAmount) * 30 / 100;
    }

    private int calculateFinalPaymentAmount(int totalPurchaseAmount, int promotionDiscountAmount, int membershipDiscountAmount) {
        return totalPurchaseAmount - promotionDiscountAmount - membershipDiscountAmount;
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public int getPromotionDiscountAmount() {
        return promotionDiscountAmount;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }

    public int getFinalPaymentAmount() {
        return finalPaymentAmount;
    }
}
