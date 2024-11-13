package store.ui;

import store.inventory.InventoryReadService;
import store.order.Order;
import store.order.OrderService;
import store.purchase.Receipt;

public abstract class UserInterfaceHandler {
    protected final InventoryReadService inventoryReadService;
    protected final OrderService orderService;

    public UserInterfaceHandler(InventoryReadService inventoryReadService, OrderService orderService) {
        this.inventoryReadService = inventoryReadService;
        this.orderService = orderService;
    }

    protected InventoryReadService getInventoryReadService() {
        return inventoryReadService;
    }

    protected OrderService getOrderService() {
        return orderService;
    }

    public abstract Order promptOrderUntilValidInputForm();

    public abstract void printIntro();

    public abstract void printReceipt(Receipt receipt);

    public abstract String promptFreeItemOffer(String productName);

    public abstract String promptNonDiscountedPurchase(String productName, int quantity);

    public abstract String promptMembershipDiscount();

    public abstract String promptForAdditionalPurchase();
}