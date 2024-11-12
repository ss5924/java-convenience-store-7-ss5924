package store.purchasesummary;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.Product;
import store.promotion.Promotion;
import store.purchasesummary.PurchaseSummary;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseSummaryTest {
    private Promotion promotion;
    private Product product;
    private LocalDateTime now = DateTimes.now();

    @BeforeEach
    void setUp() {
        product = new Product("콜라", 1000);
        promotion = new Promotion("2+1", 2, 1, now, now.plusDays(1));
    }

    @DisplayName("프로모션 혜택을 통해 반환되는 값을 계산한다. - 혜택에 맞춰 수량을 주문함")
    @Test
    void calculatePurchaseAndGiftQuantities() {
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 3, 10, 10, now);

        assertEquals(1, purchaseSummary.getPotentialGiftItems());
        assertEquals(2, purchaseSummary.getActualPurchaseQuantity());
        assertEquals(7, purchaseSummary.getRemainingPromotionStock());
        assertEquals(10, purchaseSummary.getRemainingNoPromotionStock());
        assertEquals(0, purchaseSummary.getEligibleFreeItems());
        assertEquals(0, purchaseSummary.getNonDiscountedQuantity());
    }

    @DisplayName("프로모션 혜택을 통해 반환되는 값을 계산한다. - 조건에 맞는 수량만 가져오고 증정 수량을 가져오지 않음")
    @Test
    void calculatePurchaseAndGiftQuantities3() {
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 2, 10, 10, now);

        assertEquals(0, purchaseSummary.getPotentialGiftItems());
        assertEquals(2, purchaseSummary.getActualPurchaseQuantity());
        assertEquals(8, purchaseSummary.getRemainingPromotionStock());
        assertEquals(10, purchaseSummary.getRemainingNoPromotionStock());
        assertEquals(1, purchaseSummary.getEligibleFreeItems());
        assertEquals(2, purchaseSummary.getNonDiscountedQuantity());
    }

    @DisplayName("프로모션 혜택을 통해 반환되는 값을 계산한다. - 프로모션 재고의 최대치까지 가져옴")
    @Test
    void calculatePurchaseAndGiftQuantities2() {
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 8, 8, 10, now);

        assertEquals(2, purchaseSummary.getPotentialGiftItems());
        assertEquals(6, purchaseSummary.getActualPurchaseQuantity());
        assertEquals(0, purchaseSummary.getRemainingPromotionStock());
        assertEquals(10, purchaseSummary.getRemainingNoPromotionStock());
        assertEquals(0, purchaseSummary.getEligibleFreeItems());
        assertEquals(2, purchaseSummary.getNonDiscountedQuantity());
    }

    @DisplayName("프로모션 혜택을 통해 반환되는 값을 계산한다. - 전체 재고의 최대치를 초과하여 가져옴")
    @Test
    void calculatePurchaseAndGiftQuantities4() {
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 18, 8, 10, now);

        assertEquals(2, purchaseSummary.getPotentialGiftItems());
        assertEquals(16, purchaseSummary.getActualPurchaseQuantity());
        assertEquals(0, purchaseSummary.getRemainingPromotionStock());
        assertEquals(0, purchaseSummary.getRemainingNoPromotionStock());
        assertEquals(0, purchaseSummary.getEligibleFreeItems());
        assertEquals(12, purchaseSummary.getNonDiscountedQuantity());
    }

    @DisplayName("프로모션 혜택을 통해 반환되는 값을 계산한다. - 프로모션 재고가 없음")
    @Test
    void calculatePurchaseAndGiftQuantities5() {
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 3, 0, 10, now);

        assertEquals(0, purchaseSummary.getPotentialGiftItems());
        assertEquals(3, purchaseSummary.getActualPurchaseQuantity());
        assertEquals(0, purchaseSummary.getRemainingPromotionStock());
        assertEquals(7, purchaseSummary.getRemainingNoPromotionStock());
        assertEquals(0, purchaseSummary.getEligibleFreeItems());
        assertEquals(3, purchaseSummary.getNonDiscountedQuantity());
    }
}