package store.product;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.inventory.InventoryItem;
import store.inventory.InventoryWriteService;
import store.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryWriteServiceTest {
    private InventoryWriteService inventoryWriteService;
    List<InventoryItem> inventoryItems;
    InventoryItem updatedItem;

    @BeforeEach
    void setUp() {
        inventoryWriteService = new InventoryWriteService();

        Product product = new Product("콜라", 1000);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, DateTimes.now(), DateTimes.now().plusDays(1));
        updatedItem = new InventoryItem(product, 1, promotion);

        inventoryItems = new ArrayList<>();
        InventoryItem item = new InventoryItem(product, 10, promotion);
        InventoryItem item2 = new InventoryItem(product, 10, null);
        inventoryItems.add(item);
        inventoryItems.add(item2);
    }

    @DisplayName("재고를 업데이트한다.")
    @Test
    void updateInventoryItemQuantity() {
        inventoryWriteService.updateInventoryItemQuantityWithoutPromotion(inventoryItems, updatedItem);

        InventoryItem checkItem = inventoryItems.stream()
                .filter(item -> item.getProduct().equals(updatedItem.getProduct())
                        && item.getPromotion().equals(updatedItem.getPromotion()))
                .findFirst().orElse(null);

        assertEquals(1, checkItem.getQuantity());
    }

}