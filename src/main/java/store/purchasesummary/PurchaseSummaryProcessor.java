package store.purchasesummary;

import store.order.Order;

import java.util.List;

public class PurchaseSummaryProcessor {
    private final PurchaseSummaryService purchaseSummaryService;

    public PurchaseSummaryProcessor(PurchaseSummaryService purchaseSummaryService) {
        this.purchaseSummaryService = purchaseSummaryService;
    }

    public List<PurchaseSummary> createPurchaseSummaries(Order order) {
        return purchaseSummaryService.createPurchaseSummaries(order);
    }

}
