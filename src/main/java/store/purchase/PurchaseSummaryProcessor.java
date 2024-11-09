package store.purchase;

import store.io.PromptHandler;
import store.product.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PurchaseSummaryProcessor {
    private final PromptHandler promptHandler;

    public PurchaseSummaryProcessor(PromptHandler promptHandler) {
        this.promptHandler = promptHandler;
    }

    public void updateSummariesWithAdditionalOptions(List<PurchaseSummary> summaries) {
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
                String response = promptHandler.promptFreeItemOffer(product.getName());
                summaries.stream()
                        .filter(summary -> summary.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateEligibleFreeItemsWithOrderOption(response.equals("Y")));
            }
        });
    }

    private void setNonDiscountedProductsOption(List<PurchaseSummary> summaries) {
        Map<Product, Integer> nonDiscountedProducts = summaries.stream()
                .filter(summary -> summary.getNonDiscountedQuantity() > 0)
                .collect(Collectors.toMap(
                        PurchaseSummary::getProduct,
                        PurchaseSummary::getNonDiscountedQuantity
                ));

        nonDiscountedProducts.forEach((product, quantity) -> {
            if (quantity > 0) {
                String response = promptHandler.promptNonDiscountedPurchase(product.getName(), quantity);
                summaries.stream()
                        .filter(summary -> summary.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateNonDiscountedQuantityWithOrderOption(response.equals("Y")));
            }
        });
    }
}