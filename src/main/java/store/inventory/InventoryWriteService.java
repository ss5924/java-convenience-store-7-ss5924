package store.inventory;

import store.common.AbstractFileWriteService;

import java.util.ArrayList;
import java.util.List;

public class InventoryWriteService extends AbstractFileWriteService<InventoryItem> {
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    public void updateInventoryItemQuantityWithoutPromotion(List<InventoryItem> inventoryItems, InventoryItem updatedItem) {
        inventoryItems.stream()
                .filter(item -> item.getProduct().equals(updatedItem.getProduct()))
                .findFirst()
                .ifPresent(item -> item.setQuantity(Math.max(0, item.getQuantity() - updatedItem.getQuantity()))); // 차감 로직
    }

    public void updateInventoryItemQuantityWithPromotion(List<InventoryItem> inventoryItems, InventoryItem updatedItem) {
        inventoryItems.stream()
                .filter(item -> item.getProduct().equals(updatedItem.getProduct())
                        && item.getPromotion().equals(updatedItem.getPromotion()))
                .findFirst()
                .ifPresent(item -> item.setQuantity(Math.max(0, item.getQuantity() - updatedItem.getQuantity()))); // 차감 로직
    }

    public void saveInventoryItems(List<InventoryItem> inventoryItems) {
        writeAllObjects(PRODUCT_FILE_PATH, inventoryItems);
    }

    @Override
    protected List<String> mapToStrings(InventoryItem item) {
        List<String> strings = new ArrayList<>();
        strings.add(item.getProduct().getName());
        strings.add(String.valueOf(item.getProduct().getPrice()));
        strings.add(String.valueOf(item.getQuantity()));
        strings.add(item.getPromotion() == null ? "null" : item.getPromotion().getName());

        return strings;
    }

}
