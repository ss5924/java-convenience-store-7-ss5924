package store.purchase;

import store.membership.MembershipService;
import store.order.Order;
import store.product.InventoryReadService;

import java.time.LocalDateTime;
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

    public Receipt createReceipt(List<PurchaseSummary> purchaseSummaries, boolean isMembershipDiscount) {
        Purchase purchase = createPurchase(purchaseSummaries, isMembershipDiscount);
        return new Receipt(purchase);
    }

    public Purchase createPurchase(List<PurchaseSummary> purchaseSummaries, boolean isMembershipDiscount) {
        List<Item> purchaseItems = getPurchaseItem(purchaseSummaries);
        List<Item> giftItems = getGiftItems(purchaseSummaries);
        Payment payment = createPayment(purchaseItems, giftItems, isMembershipDiscount);

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

    private Payment createPayment(List<Item> purchaseItems, List<Item> giftItems, boolean isMembershipDiscount) {
        return paymentFactory.create(purchaseItems, giftItems, membershipService, "user_id1", isMembershipDiscount);
    }

    public List<PurchaseSummary> createPurchaseSummaries(Order order, LocalDateTime now) {
        return purchaseSummaryFactory.createPurchaseSummaries(order, inventoryReadService, now);
    }

}
