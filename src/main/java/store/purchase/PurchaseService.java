package store.purchase;

import store.membership.MembershipService;

import java.util.ArrayList;
import java.util.List;

public class PurchaseService {
    private MembershipService membershipService;
    private PaymentFactory paymentFactory;

    public PurchaseService(MembershipService membershipService, PaymentFactory paymentFactory) {
        this.membershipService = membershipService;
        this.paymentFactory = paymentFactory;
    }

    public Receipt createReceipt(List<PurchaseSummary> purchaseSummaries) {
        Purchase purchase = createPurchase(purchaseSummaries);
        return new Receipt(purchase);
    }

    private Purchase createPurchase(List<PurchaseSummary> purchaseSummaries) {
        List<Item> purchaseItems = getPurchaseItem(purchaseSummaries);
        List<Item> giftItems = getGiftItems(purchaseSummaries);
        Payment payment = createPayment(purchaseItems, giftItems);

        return new Purchase(purchaseItems, giftItems, payment);
    }

    private List<Item> getPurchaseItem(List<PurchaseSummary> purchaseSummaries) {
        List<Item> purchaseItems = new ArrayList<>();
        for(PurchaseSummary summary : purchaseSummaries) {
            purchaseItems.add(summary.toPurchaseItem());
        }
        return purchaseItems;
    }

    private List<Item> getGiftItems(List<PurchaseSummary> purchaseSummaries) {
        List<Item> giftItems = new ArrayList<>();
        for(PurchaseSummary summary : purchaseSummaries) {
            if(summary.getPotentialGiftItems() == 0) {
                break;
            }
            giftItems.add(summary.toGiftItem());
        }
        return giftItems;
    }

    private Payment createPayment(List<Item> purchaseItems, List<Item> giftItems) {
        return paymentFactory.create(purchaseItems, giftItems, membershipService, "user_id1");
    }

}
