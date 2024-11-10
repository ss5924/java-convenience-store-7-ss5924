package store.inventory;

import store.purchasesummary.PurchaseSummary;

import java.util.List;

public class InventoryProcessor {
    private final InventoryReadService inventoryReadService;
    private final InventoryUpdateManager inventoryUpdateManager;

    public InventoryProcessor(InventoryReadService inventoryReadService, InventoryUpdateManager inventoryUpdateManager) {
        this.inventoryReadService = inventoryReadService;
        this.inventoryUpdateManager = inventoryUpdateManager;
    }

    public void updateInventory(List<PurchaseSummary> summaries) {
        List<InventoryItem> allItems = inventoryReadService.getAllInventoryItems();
        inventoryUpdateManager.updateInventoryFromSummaries(allItems, summaries);
    }
}