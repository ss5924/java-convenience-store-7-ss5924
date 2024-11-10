package store.inventory;

import store.product.Product;
import store.purchasesummary.PurchaseSummary;

import java.util.List;

public class InventoryUpdateManager {
    private final InventoryWriteService inventoryWriteService;

    public InventoryUpdateManager(InventoryWriteService inventoryWriteService) {
        this.inventoryWriteService = inventoryWriteService;
    }

    public void updateInventoryFromSummaries(List<InventoryItem> inventoryItems, List<PurchaseSummary> summaries) {
        summaries.forEach(summary -> {
            Product product = summary.getProduct();
            int totalDeductQuantity = summary.getActualPurchaseQuantity() + summary.getPotentialGiftItems();

            InventoryItem updatedItem = new InventoryItem(product, totalDeductQuantity, summary.getPromotion());

            if (summary.getPromotion() == null) {
                inventoryWriteService.updateInventoryItemQuantityWithoutPromotion(inventoryItems, updatedItem);
            } else {
                inventoryWriteService.updateInventoryItemQuantityWithPromotion(inventoryItems, updatedItem);
            }
        });

        inventoryWriteService.saveInventoryItems(inventoryItems);
    }
}