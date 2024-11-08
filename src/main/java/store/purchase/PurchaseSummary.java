package store.purchase;

import store.product.Product;
import store.promotion.Promotion;

public class PurchaseSummary {
    private final Product product;
    private final Promotion promotion;
    private int actualPurchaseQuantity; // 최종 결제할 수량
    private int potentialGiftItems; // 증정 가능 횟수
    private int remainingPromotionStock; // 구매 후 프로모션 재고
    private int remainingNoPromotionStock; // 구매 후 일반 재고
    private int eligibleFreeItems; // 현재 주문한 수량에서 프로모션 혜택을 계산했을 때 추가 주문을 통해 받을 수 있는 무료 증정 수량
    private int nonDiscountedQuantity; // 프로모션 재고 부족으로 할인이 적용되지 않는 수량

    public PurchaseSummary(Product product, Promotion promotion, int orderedQuantity, int promotionStock, int noPromotionStock) {
        this.product = product;
        this.promotion = promotion;

        calculateSummary(orderedQuantity, promotionStock, noPromotionStock);
    }

    public Item toPurchaseItem() {
        if (actualPurchaseQuantity > 0) {
            return new PurchaseItem(product, actualPurchaseQuantity);
        }
        return null;
    }

    public Item toGiftItem() {
        if (eligibleFreeItems > 0) {
            return new GiftItem(product, actualPurchaseQuantity);
        }
        return null;
    }

    private void calculateSummary(int orderedQuantity, int promotionStock, int noPromotionStock) {
        int actualProcessOrderedQuantity = Math.min(orderedQuantity, promotionStock);
        int giftSets = getGiftSets(actualProcessOrderedQuantity, promotionStock);
        int potentialGiftItems = getPotentialGiftItems(giftSets);

        int actualPurchaseQuantity = getActualPurchaseQuantity(actualProcessOrderedQuantity, giftSets);
        int remainingPromotionStock = promotionStock - actualProcessOrderedQuantity;

        int remainingOrderedQuantity = orderedQuantity - actualProcessOrderedQuantity;
        int additionalPurchaseQuantity = Math.min(remainingOrderedQuantity, noPromotionStock);
        int remainingNoPromotionStock = noPromotionStock - additionalPurchaseQuantity;

        int eligibleFreeItems = getEligibleFreeItems(actualProcessOrderedQuantity + additionalPurchaseQuantity, remainingPromotionStock);

        int nonDiscountedQuantity = getNonDiscountedQuantity(orderedQuantity, actualProcessOrderedQuantity, giftSets, noPromotionStock);

        this.actualPurchaseQuantity = actualPurchaseQuantity + additionalPurchaseQuantity;
        this.potentialGiftItems = potentialGiftItems;
        this.remainingPromotionStock = remainingPromotionStock;
        this.remainingNoPromotionStock = remainingNoPromotionStock;
        this.eligibleFreeItems = eligibleFreeItems;
        this.nonDiscountedQuantity = nonDiscountedQuantity;
    }

    private int getGiftSets(int orderedQuantity, int availableStock) {
        return Math.min(orderedQuantity / (promotion.getRequiredCondition() + promotion.getGiftQuantity()),
                availableStock / (promotion.getRequiredCondition() + promotion.getGiftQuantity()));
    }

    private int getActualPurchaseQuantity(int orderedQuantity, int giftSets) {
        int potentialGiftItems = giftSets * promotion.getGiftQuantity();
        return orderedQuantity - potentialGiftItems;
    }

    private int getPotentialGiftItems(int giftSets) {
        return giftSets * promotion.getGiftQuantity();
    }

    private int getEligibleFreeItems(int orderedQuantity, int remainingStock) {
        if (orderedQuantity % promotion.getRequiredCondition() == 0 && remainingStock > 0) {
            return promotion.getGiftQuantity();
        }
        return 0;
    }

    private int getNonDiscountedQuantity(int orderedQuantity, int actualProcessOrderedQuantity, int giftSets, int noPromotionStock) {
        int discountedQuantity = giftSets * (promotion.getRequiredCondition() + promotion.getGiftQuantity());
        int insufficientPromotionStockQuantity = Math.max(0, actualProcessOrderedQuantity - discountedQuantity);
        int remainingOrderedQuantity = orderedQuantity - actualProcessOrderedQuantity;
        int nonPromotionQuantity = Math.min(remainingOrderedQuantity, noPromotionStock);
        return insufficientPromotionStockQuantity + nonPromotionQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getActualPurchaseQuantity() {
        return actualPurchaseQuantity;
    }

    public int getPotentialGiftItems() {
        return potentialGiftItems;
    }

    public int getRemainingPromotionStock() {
        return remainingPromotionStock;
    }

    public int getRemainingNoPromotionStock() {
        return remainingNoPromotionStock;
    }

    public int getEligibleFreeItems() {
        return eligibleFreeItems;
    }

    public int getNonDiscountedQuantity() {
        return nonDiscountedQuantity;
    }
}
