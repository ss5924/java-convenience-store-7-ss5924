package store.order;

import store.io.PromptHandler;
import store.product.Product;
import store.purchasesummary.PurchaseSummary;

import java.util.List;

public class OptionalOrderingProcessor {

    public void updateSummariesWithOptionalOrdering(List<PurchaseSummary> summaries) {
        setEligibleFreeItemsOption(summaries);
        setNonDiscountedProductsOption(summaries);
    }

    private void setEligibleFreeItemsOption(List<PurchaseSummary> summaries) {
        summaries.stream()
                .filter(summary -> summary.getEligibleFreeItems() > 0)
                .forEach(summary -> {
                    Product product = summary.getProduct();
                    int requiredCondition = summary.getPromotion().getRequiredCondition();
                    int giftQuantity = summary.getPromotion().getGiftQuantity();
                    if (summary.getActualPurchaseQuantity() % (requiredCondition + giftQuantity) != 0) {
                        String response = PromptHandler.promptFreeItemOffer(product.getName());
                        summary.updateEligibleFreeItemsWithOrderOption(response.equals("Y"));
                    }
                });
    }

    private void setNonDiscountedProductsOption(List<PurchaseSummary> summaries) {
        summaries.stream()
                .filter(summary -> summary.getRemainingPromotionStock() > 0)
                .forEach(summary -> {
                    int nonDiscountedQuantity = summary.getNonDiscountedQuantity();
                    Product product = summary.getProduct();
                    if (nonDiscountedQuantity > 0) {
                        String response = PromptHandler.promptNonDiscountedPurchase(product.getName(), nonDiscountedQuantity);
                        summary.updateNonDiscountedQuantityWithOrderOption(response.equals("Y"));
                    }
                });
    }
}