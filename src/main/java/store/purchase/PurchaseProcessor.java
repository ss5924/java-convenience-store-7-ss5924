package store.purchase;

import store.purchasesummary.PurchaseSummary;

import java.util.List;

public class PurchaseProcessor {
    private final PurchaseService purchaseService;

    public PurchaseProcessor(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public Receipt processPurchase(List<PurchaseSummary> summaries, String userId, boolean isMembershipDiscount) {
        return purchaseService.createReceipt(summaries, userId, isMembershipDiscount);
    }
}
