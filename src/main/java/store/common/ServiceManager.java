package store.common;

import store.input.InputToOrderConverter;
import store.input.OutputMessageManager;
import store.input.PromptInputMessageManager;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.product.InventoryReadService;
import store.product.InventoryWriteService;
import store.product.ProductService;
import store.promotion.PromotionService;
import store.purchase.PaymentFactory;
import store.purchase.PurchaseService;
import store.purchase.PurchaseSummaryFactory;

public class ServiceManager {
    private final MembershipManager membershipManager;
    private final ProductService productService;
    private final PromotionService promotionService;
    private final MembershipService membershipService;
    private final InventoryReadService inventoryReadService;
    private final PaymentFactory paymentFactory;
    private final PurchaseSummaryFactory purchaseSummaryFactory;
    private final PurchaseService purchaseService;
    private final OutputMessageManager outputMessageManager;
    private final PromptInputMessageManager promptInputMessageManager;
    private final InputToOrderConverter inputToOrderConverter;
    private final InventoryWriteService inventoryWriteService;

    public ServiceManager() {
        this.membershipManager = new MembershipManager();
        this.productService = new ProductService();
        this.promotionService = new PromotionService();
        this.membershipService = new MembershipService(membershipManager);
        this.inventoryReadService = new InventoryReadService(productService, promotionService);
        this.paymentFactory = new PaymentFactory();
        this.purchaseSummaryFactory = new PurchaseSummaryFactory();
        this.purchaseService = new PurchaseService(membershipService, inventoryReadService, paymentFactory, purchaseSummaryFactory);
        this.outputMessageManager = new OutputMessageManager(purchaseService, inventoryReadService);
        this.promptInputMessageManager = new PromptInputMessageManager(outputMessageManager);
        this.inputToOrderConverter = new InputToOrderConverter(productService);
        this.inventoryWriteService = new InventoryWriteService();
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

    public OutputMessageManager getOutputMessageManager() {
        return outputMessageManager;
    }

    public PromptInputMessageManager getPromptInputMessageManager() {
        return promptInputMessageManager;
    }

    public InputToOrderConverter getInputToOrderConverter() {
        return inputToOrderConverter;
    }

    public InventoryWriteService getInventoryWriteService() {
        return inventoryWriteService;
    }
}