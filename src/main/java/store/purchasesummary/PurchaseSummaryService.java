package store.purchasesummary;

import store.inventory.InventoryReadService;
import store.order.Order;

import java.util.List;

public class PurchaseSummaryService {
    private final PurchaseSummaryFactory purchaseSummaryFactory;
    private final InventoryReadService inventoryReadService;

    public PurchaseSummaryService(PurchaseSummaryFactory purchaseSummaryFactory, InventoryReadService inventoryReadService) {
        this.purchaseSummaryFactory = purchaseSummaryFactory;
        this.inventoryReadService = inventoryReadService;
    }

    public List<PurchaseSummary> createPurchaseSummaries(Order order) {
        return purchaseSummaryFactory.createPurchaseSummaries(order, inventoryReadService);
    }
}
