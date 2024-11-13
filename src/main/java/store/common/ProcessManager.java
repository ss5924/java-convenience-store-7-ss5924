package store.common;

import store.inventory.InventoryProcessor;
import store.order.OptionalOrderingProcessor;
import store.order.OrderProcessor;
import store.purchase.PurchaseProcessor;
import store.purchasesummary.PurchaseSummaryProcessor;

public class ProcessManager {
    private final OrderProcessor orderProcessor;
    private final OptionalOrderingProcessor optionalOrderingProcessor;
    private final InventoryProcessor inventoryProcessor;
    private final PurchaseProcessor purchaseProcessor;
    private final PurchaseSummaryProcessor purchaseSummaryProcessor;

    public ProcessManager(ServiceManager serviceManager) {
        this.orderProcessor = new OrderProcessor(serviceManager.getGraphicUIHandler());
        this.optionalOrderingProcessor = new OptionalOrderingProcessor(serviceManager.getGraphicUIHandler());
        this.inventoryProcessor = new InventoryProcessor(
                serviceManager.getInventoryReadService(), serviceManager.getInventoryUpdateManager());
        this.purchaseProcessor = new PurchaseProcessor(serviceManager.getPurchaseService());
        this.purchaseSummaryProcessor = new PurchaseSummaryProcessor(serviceManager.getPurchaseSummaryService());
    }

    public OrderProcessor getOrderProcessor() {
        return orderProcessor;
    }

    public OptionalOrderingProcessor getOptionalOrderingProcessor() {
        return optionalOrderingProcessor;
    }

    public InventoryProcessor getInventoryProcessor() {
        return inventoryProcessor;
    }

    public PurchaseProcessor getPurchaseProcessor() {
        return purchaseProcessor;
    }

    public PurchaseSummaryProcessor getPurchaseSummaryProcessor() {
        return purchaseSummaryProcessor;
    }
}
