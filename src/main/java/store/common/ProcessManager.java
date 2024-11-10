package store.common;

import store.inventory.InventoryProcessor;
import store.order.OptionalOrderingProcessor;
import store.order.OrderProcessor;
import store.purchase.PurchaseProcessor;

public class ProcessManager {
    private final OrderProcessor orderProcessor;
    private final OptionalOrderingProcessor optionalOrderingProcessor;
    private final InventoryProcessor inventoryProcessor;
    private final PurchaseProcessor purchaseProcessor;

    public ProcessManager(ServiceManager serviceManager) {
        this.orderProcessor = new OrderProcessor(serviceManager.getPromptHandler());
        this.optionalOrderingProcessor = new OptionalOrderingProcessor();
        this.inventoryProcessor = new InventoryProcessor(
                serviceManager.getInventoryReadService(), serviceManager.getInventoryUpdateManager());
        this.purchaseProcessor = new PurchaseProcessor(serviceManager.getPurchaseService());

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
}
