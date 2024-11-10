package store.purchasesummary;

import store.product.Product;
import store.promotion.Promotion;
import store.purchase.GiftItem;
import store.purchase.Item;
import store.purchase.PurchaseItem;

import java.time.LocalDateTime;

public class PurchaseSummary {
    private final Product product;
    private final Promotion promotion;
    private int actualPurchaseQuantity; // 최종 결제할 수량
    private int potentialGiftItems; // 증정 가능 횟수
    private int remainingPromotionStock; // 구매 후 프로모션 재고
    private int remainingNoPromotionStock; // 구매 후 일반 재고
    private int eligibleFreeItems; // 현재 주문한 수량에서 프로모션 혜택을 계산했을 때 추가 주문을 통해 받을 수 있는 무료 증정 수량
    private int nonDiscountedQuantity; // 프로모션 재고 부족으로 할인이 적용되지 않는 수량

    public PurchaseSummary(Product product, Promotion promotion, int orderedQuantity, int promotionStock, int noPromotionStock, LocalDateTime now) {
        this.product = product;
        this.promotion = isPromotionValid(promotion, now) ? promotion : null;

        validate(orderedQuantity, promotionStock, noPromotionStock);

        calculateSummary(orderedQuantity, promotionStock, noPromotionStock);
    }

    private boolean isPromotionValid(Promotion promotion, LocalDateTime now) {
        if (promotion == null) {
            return false;
        }
        return promotion.isValidPeriod(now);
    }

    public boolean isPromotionValid(LocalDateTime now) {
        return isPromotionValid(promotion, now);
    }

    private void validate(int orderedQuantity, int promotionStock, int noPromotionStock) {
        if (orderedQuantity > promotionStock + noPromotionStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    public void updateEligibleFreeItemsWithOrderOption(boolean isEligibleFreeItem) {
        if (isEligibleFreeItem && remainingPromotionStock >= 1) {
            this.eligibleFreeItems = 0;
            this.nonDiscountedQuantity = 0;
            this.potentialGiftItems = 1;
            this.remainingPromotionStock -= 1;
        }
    }

    public void updateNonDiscountedQuantityWithOrderOption(boolean isNonDiscountedQuantity) {
        if (!isNonDiscountedQuantity) {
            this.actualPurchaseQuantity -= this.nonDiscountedQuantity;
            this.remainingNoPromotionStock += this.nonDiscountedQuantity; // todo
            this.nonDiscountedQuantity = 0;
        }
    }

    public Item toPurchaseItem() {
        return new PurchaseItem(product, actualPurchaseQuantity);
    }

    public Item toGiftItem() {
        return new GiftItem(product, potentialGiftItems);
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
        if (promotion == null) {
            return 0;
        }
        return Math.min(orderedQuantity / (promotion.getRequiredCondition() + promotion.getGiftQuantity()),
                availableStock / (promotion.getRequiredCondition() + promotion.getGiftQuantity()));
    }

    private int getActualPurchaseQuantity(int orderedQuantity, int giftSets) {
        int potentialGiftItems = getPotentialGiftItems(giftSets);
        return orderedQuantity - potentialGiftItems;
    }

    private int getPotentialGiftItems(int giftSets) {
        if (promotion == null) {
            return 0;
        }
        return giftSets * promotion.getGiftQuantity();
    }

    private int getEligibleFreeItems(int orderedQuantity, int remainingStock) {
        if (promotion != null && orderedQuantity % promotion.getRequiredCondition() == 0 && remainingStock > 0) {
            return promotion.getGiftQuantity();
        }
        return 0;
    }

    private int getNonDiscountedQuantity(int orderedQuantity, int actualProcessOrderedQuantity, int giftSets, int noPromotionStock) {
        int discountedQuantity = 0;
        if (promotion != null) {
            discountedQuantity = giftSets * (promotion.getRequiredCondition() + promotion.getGiftQuantity());
        }
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

    public Promotion getPromotion() {
        return promotion;
    }
}
