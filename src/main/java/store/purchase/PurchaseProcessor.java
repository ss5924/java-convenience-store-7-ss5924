package store.purchase;

import java.util.List;

public class PurchaseProcessor {
    private final PurchaseService purchaseService;

    public PurchaseProcessor(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public Receipt purchase(List<PurchaseSummary> summaries, String userId, boolean isMembershipDiscount) {
        return purchaseService.createReceipt(summaries, userId, isMembershipDiscount);
    }
}
