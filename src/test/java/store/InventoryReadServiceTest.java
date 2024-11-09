package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.product.InventoryItem;
import store.product.InventoryReadService;
import store.product.Product;
import store.product.ProductService;
import store.promotion.PromotionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryReadServiceTest {
    private InventoryReadService inventoryReadService;
    private static final LocalDateTime now = DateTimes.now();
    private Product cola;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductService();
        PromotionService promotionService = new PromotionService();
        inventoryReadService = new InventoryReadService(productService, promotionService);
        cola = new Product("콜라", 1000);
    }

    @DisplayName("상품명으로 재고를 가져와 프로모션 진행 상품과 일반 상품을 확인한다.")
    @Test
    void getInventoryItem() {
        Product product = new Product("콜라", 1000);
        List<InventoryItem> inventoryItems = inventoryReadService.getInventoryItemsByProduct(product, now);

        assertEquals(2, inventoryItems.size());
        assertEquals("콜라", inventoryItems.getFirst().getProduct().getName());
        assertEquals("콜라", inventoryItems.getLast().getProduct().getName());
        assertEquals("탄산2+1", inventoryItems.getFirst().getPromotion().getName());
        assertNull(inventoryItems.getLast().getPromotion());
    }

    @DisplayName("프로모션 기간이 유효한 재고 및 프로모션이 없는 재고를 가져온다.")
    @Test
    void getInventoryItemsWithinValidPeriod() {
        List<InventoryItem> inventoryItems = inventoryReadService.getInventoryItemsWithinValidPeriod(now);

        assertEquals(16, inventoryItems.size());
        assertEquals("컵라면", inventoryItems.getLast().getProduct().getName());
        assertNull(inventoryItems.getLast().getPromotion());
    }

    @Test
    void 프로모션이_없는_재고의_프로모션이_null인지_확인한다() {
        List<InventoryItem> inventoryItems = inventoryReadService.getAllInventoryItems();

        assertNull(inventoryItems.get(1).getPromotion());
    }

    @DisplayName("전체 재고를 가져와 갯수를 체크하고 첫번째와 마지막 항목이 맞는지 확인한다.")
    @Test
    void getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryReadService.getAllInventoryItems();

        assertEquals(17, inventoryItems.size());
        assertEquals("콜라", inventoryItems.getFirst().getProduct().getName());
        assertEquals("탄산2+1", inventoryItems.getFirst().getPromotion().getName());
        assertEquals("컵라면", inventoryItems.getLast().getProduct().getName());
        assertEquals("기간만료프로모션", inventoryItems.getLast().getPromotion().getName());
    }

    @DisplayName("상품의 전체 갯수를 확인한다.")
    @Test
    void getCanApplyTotalQuantity() {
        int quantity = inventoryReadService.getInventoryItemsByProduct(cola, now).stream().mapToInt(InventoryItem::getQuantity).sum();

        assertEquals(20, quantity);
    }

}