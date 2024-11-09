package store.purchase;

import store.membership.MembershipService;
import store.order.Order;
import store.product.InventoryReadService;

import java.util.ArrayList;
import java.util.List;

public class PurchaseService {
    private final MembershipService membershipService;
    private final InventoryReadService inventoryReadService;
    private final PaymentFactory paymentFactory;
    private final PurchaseSummaryFactory purchaseSummaryFactory;

    public PurchaseService(MembershipService membershipService, InventoryReadService inventoryReadService, PaymentFactory paymentFactory, PurchaseSummaryFactory purchaseSummaryFactory) {
        this.membershipService = membershipService;
        this.inventoryReadService = inventoryReadService;
        this.paymentFactory = paymentFactory;
        this.purchaseSummaryFactory = purchaseSummaryFactory;
    }

    public Receipt createReceipt(List<PurchaseSummary> purchaseSummaries, String userId, boolean isMembershipDiscount) {
        Purchase purchase = createPurchase(purchaseSummaries, userId, isMembershipDiscount);
        return new Receipt(purchase);
    }

    public Purchase createPurchase(List<PurchaseSummary> purchaseSummaries, String userId, boolean isMembershipDiscount) {
        List<Item> purchaseItems = getPurchaseItem(purchaseSummaries);
        List<Item> giftItems = getGiftItems(purchaseSummaries);
        Payment payment = createPayment(purchaseItems, giftItems, userId, isMembershipDiscount);

        return new Purchase(purchaseItems, giftItems, payment);
    }

    private List<Item> getPurchaseItem(List<PurchaseSummary> purchaseSummaries) {
        List<Item> purchaseItems = new ArrayList<>();
        for (PurchaseSummary summary : purchaseSummaries) {
            purchaseItems.add(summary.toPurchaseItem());
        }
        return purchaseItems;
    }

    private List<Item> getGiftItems(List<PurchaseSummary> purchaseSummaries) {
        List<Item> giftItems = new ArrayList<>();
        for (PurchaseSummary summary : purchaseSummaries) {
            if (summary.getPotentialGiftItems() == 0) {
                break;
            }
            giftItems.add(summary.toGiftItem());
        }
        return giftItems;
    }

    private Payment createPayment(List<Item> purchaseItems, List<Item> giftItems, String userId, boolean isMembershipDiscount) {
        return paymentFactory.create(purchaseItems, giftItems, membershipService, userId, isMembershipDiscount);
    }

    public List<PurchaseSummary> createPurchaseSummaries(Order order) {
        return purchaseSummaryFactory.createPurchaseSummaries(order, inventoryReadService);
    }

}
