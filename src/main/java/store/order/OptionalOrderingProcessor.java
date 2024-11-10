package store.order;

import store.io.PromptHandler;
import store.product.Product;
import store.purchasesummary.PurchaseSummary;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OptionalOrderingProcessor {

    public void updateSummariesWithOptionalOrdering(List<PurchaseSummary> summaries) {
        setEligibleFreeItemsOption(summaries);
        setNonDiscountedProductsOption(summaries);
    }

    private void setEligibleFreeItemsOption(List<PurchaseSummary> summaries) {
        Map<Product, Integer> eligibleFreeItems = summaries.stream()
                .filter(summary -> summary.getEligibleFreeItems() > 0)
                .collect(Collectors.toMap(
                        PurchaseSummary::getProduct,
                        PurchaseSummary::getEligibleFreeItems
                ));

        eligibleFreeItems.forEach((product, quantity) -> {
            if (quantity > 0) {
                String response = PromptHandler.promptFreeItemOffer(product.getName());
                summaries.stream()
                        .filter(summary -> summary.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateEligibleFreeItemsWithOrderOption(response.equals("Y")));
            }
        });
    }

    private void setNonDiscountedProductsOption(List<PurchaseSummary> summaries) {
        boolean hasPromotionStock = summaries.stream()
                .anyMatch(summary -> summary.getRemainingPromotionStock() > 0);
        if (!hasPromotionStock) {
            return;
        }

        Map<Product, Integer> nonDiscountedProducts = summaries.stream()
                .filter(summary -> summary.getNonDiscountedQuantity() > 0)
                .collect(Collectors.toMap(
                        PurchaseSummary::getProduct,
                        PurchaseSummary::getNonDiscountedQuantity
                ));

        nonDiscountedProducts.forEach((product, quantity) -> {
            if (quantity > 0) {
                String response = PromptHandler.promptNonDiscountedPurchase(product.getName(), quantity);
                summaries.stream()
                        .filter(summary -> summary.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateNonDiscountedQuantityWithOrderOption(response.equals("Y")));
            }
        });
    }
}