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

            InventoryItem updatedItemNoPromotionStock = new InventoryItem(product, summary.getRemainingNoPromotionStock(), null);
            InventoryItem updatedItemPromotionStock = new InventoryItem(product, summary.getRemainingPromotionStock(), summary.getPromotion());

            inventoryWriteService.updateInventoryItemQuantityWithoutPromotion(inventoryItems, updatedItemNoPromotionStock);
            if (summary.getPromotion() != null) {
                inventoryWriteService.updateInventoryItemQuantityWithPromotion(inventoryItems, updatedItemPromotionStock);
            }
        });

        inventoryWriteService.saveInventoryItems(inventoryItems);
    }
}