package store.product;

import store.io.PromptHandler;
import store.purchase.PurchaseService;
import store.purchase.PurchaseSummary;
import store.purchase.Receipt;

import java.util.List;

public class InventoryProcessor {
    private final InventoryReadService inventoryReadService;
    private final InventoryUpdateManager inventoryUpdateManager;
    private final PurchaseService purchaseService;
    private final PromptHandler promptHandler;

    public InventoryProcessor(InventoryReadService inventoryReadService, InventoryUpdateManager inventoryUpdateManager,
                              PurchaseService purchaseService, PromptHandler promptHandler) {
        this.inventoryReadService = inventoryReadService;
        this.inventoryUpdateManager = inventoryUpdateManager;
        this.purchaseService = purchaseService;
        this.promptHandler = promptHandler;
    }

    public void updateInventoryAndPrintReceipt(List<PurchaseSummary> summaries, String userId, boolean isMembershipDiscount) {
        List<InventoryItem> allItems = inventoryReadService.getAllInventoryItems();
        inventoryUpdateManager.updateInventoryFromSummaries(allItems, summaries);

        Receipt receipt = purchaseService.createReceipt(summaries, userId, isMembershipDiscount);
        promptHandler.printReceipt(receipt);
    }
}