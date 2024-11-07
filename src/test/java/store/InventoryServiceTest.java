package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryServiceTest {
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductService();
        PromotionService promotionService = new PromotionService();
        inventoryService = new InventoryService(productService, promotionService);
    }

    @DisplayName("상품명으로 재고를 가져와 프로모션 진행 상품과 일반 상품을 확인한다.")
    @Test
    void getInventoryItem() {
        List<InventoryItem> inventoryItems = inventoryService.getInventoryItems("콜라");

        assertEquals(2, inventoryItems.size());
        assertEquals("콜라", inventoryItems.getFirst().getProduct().getName());
        assertEquals("콜라", inventoryItems.getLast().getProduct().getName());
        assertEquals("탄산2+1", inventoryItems.getFirst().getPromotion().getName());
        assertNull(inventoryItems.getLast().getPromotion());
    }

    @Test
    void getInventoryItemsByProductNames() {
        List<String> productNames = new ArrayList<>();
        productNames.add("콜라");
        productNames.add("사이다");
        productNames.add("감자칩");

        List<InventoryItem> inventoryItems = inventoryService.getInventoryItems(productNames);
        assertEquals(6, inventoryItems.size());
    }

    @DisplayName("프로모션 기간이 유효한 재고 및 프로모션이 없는 재고를 가져온다.")
    @Test
    void getInventoryItemsWithinValidPeriod() {
        List<InventoryItem> inventoryItems = inventoryService.getInventoryItemsWithinValidPeriod(DateTimes.now());

        assertEquals(16, inventoryItems.size());
        assertEquals("컵라면", inventoryItems.getLast().getProduct().getName());
        assertNull(inventoryItems.getLast().getPromotion());
    }

    @Test
    void 프로모션이_없는_재고의_프로모션이_null인지_확인한다() {
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();

        assertNull(inventoryItems.get(1).getPromotion());
    }

    @DisplayName("전체 재고를 가져와 갯수를 체크하고 첫번째와 마지막 항목이 맞는지 확인한다.")
    @Test
    void getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();

        assertEquals(17, inventoryItems.size());
        assertEquals("콜라", inventoryItems.getFirst().getProduct().getName());
        assertEquals("탄산2+1", inventoryItems.getFirst().getPromotion().getName());
        assertEquals("컵라면", inventoryItems.getLast().getProduct().getName());
        assertEquals("기간만료프로모션", inventoryItems.getLast().getPromotion().getName());
    }

}