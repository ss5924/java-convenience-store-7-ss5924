package store.common;

import store.inventory.InventoryReadService;
import store.inventory.InventoryUpdateManager;
import store.inventory.InventoryWriteService;
import store.ui.GraphicUIHandler;
import store.io.InputToOrderConverter;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.order.OrderService;
import store.payment.PaymentFactory;
import store.product.ProductService;
import store.promotion.PromotionService;
import store.purchase.PurchaseService;
import store.purchasesummary.PurchaseSummaryFactory;
import store.purchasesummary.PurchaseSummaryService;

public class ServiceManager {
    private final MembershipManager membershipManager;
    private final InventoryUpdateManager inventoryUpdateManager;
    private final PurchaseSummaryFactory purchaseSummaryFactory;
    private final PaymentFactory paymentFactory;
    private final InputToOrderConverter inputToOrderConverter;
    private final OrderService orderService;
    private final ProductService productService;
    private final PromotionService promotionService;
    private final MembershipService membershipService;
    private final InventoryReadService inventoryReadService;
    private final PurchaseSummaryService purchaseSummaryService;
    private final PurchaseService purchaseService;
    private final InventoryWriteService inventoryWriteService;
    private final GraphicUIHandler graphicUIHandler;

    public ServiceManager() {
        this.membershipManager = new MembershipManager();
        this.paymentFactory = new PaymentFactory();
        this.purchaseSummaryFactory = new PurchaseSummaryFactory();
        this.productService = new ProductService();
        this.promotionService = new PromotionService();
        this.inventoryWriteService = new InventoryWriteService();
        this.membershipService = new MembershipService(membershipManager);
        this.inventoryReadService = new InventoryReadService(productService, promotionService);
        this.purchaseService = new PurchaseService(membershipService, paymentFactory);
        this.inputToOrderConverter = new InputToOrderConverter(productService);
        this.orderService = new OrderService(inventoryReadService, inputToOrderConverter);
        this.inventoryUpdateManager = new InventoryUpdateManager(inventoryWriteService);
        this.purchaseSummaryService = new PurchaseSummaryService(purchaseSummaryFactory, inventoryReadService);
        this.graphicUIHandler = new GraphicUIHandler(inventoryReadService, orderService);
    }

    public MembershipManager getMembershipManager() {
        return membershipManager;
    }

    public ProductService getProductService() {
        return productService;
    }

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public MembershipService getMembershipService() {
        return membershipService;
    }

    public InventoryReadService getInventoryReadService() {
        return inventoryReadService;
    }

    public PaymentFactory getPaymentFactory() {
        return paymentFactory;
    }

    public PurchaseSummaryFactory getPurchaseSummaryFactory() {
        return purchaseSummaryFactory;
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    public InputToOrderConverter getInputToOrderConverter() {
        return inputToOrderConverter;
    }

    public InventoryWriteService getInventoryWriteService() {
        return inventoryWriteService;
    }

    public InventoryUpdateManager getInventoryUpdateManager() {
        return inventoryUpdateManager;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PurchaseSummaryService getPurchaseSummaryService() {
        return purchaseSummaryService;
    }

    public GraphicUIHandler getGraphicUIHandler() {
        return graphicUIHandler;
    }
}