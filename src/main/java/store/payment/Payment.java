package store.payment;

import store.membership.MembershipService;
import store.purchase.Item;

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
            throw new IllegalArgumentException("[ERROR] list는 null이 될 수 없습니다.");
        }

        if (giftItems == null) {
            throw new IllegalArgumentException("[ERROR] list는 null이 될 수 없습니다.");
        }
    }

    private void initialize(List<Item> purchaseItems, List<Item> giftItems) {
        this.promotionDiscountAmount = calculatePromotionDiscountAmount(giftItems);
        this.totalPurchaseAmount = calculateTotalPurchaseAmount(purchaseItems, promotionDiscountAmount);
        this.finalPaymentAmount = calculateFinalPaymentAmount();
    }

    private int calculateTotalPurchaseAmount(List<Item> purchaseItems, int promotionDiscountAmount) {
        return purchaseItems.stream().mapToInt(Item::getAmount).sum() + promotionDiscountAmount;
    }

    private int calculatePromotionDiscountAmount(List<Item> giftItems) {
        return giftItems.stream().mapToInt(Item::getDiscountAmount).sum();
    }

    private int calculateFinalPaymentAmount() {
        return totalPurchaseAmount - promotionDiscountAmount - membershipDiscountAmount;
    }

    public void applyMembershipDiscount(MembershipService membershipService, String userId) {
        int calculatedDiscount = membershipService.calculateMembershipDiscount(totalPurchaseAmount - promotionDiscountAmount);
        this.membershipDiscountAmount = membershipService.applyMembershipDiscount(userId, calculatedDiscount);
        this.finalPaymentAmount = calculateFinalPaymentAmount();
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
