package store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.ServiceManager;
import store.io.PromptMessageManager;
import store.order.Order;
import store.product.InventoryItem;
import store.product.Product;
import store.purchase.PurchaseSummary;
import store.purchase.Receipt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    private final ServiceManager serviceManager = new ServiceManager();
    private final LocalDateTime now = DateTimes.now();
    private static final String USER_ID = "SAMPLE_USER_ID_1";

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        boolean continueShopping = true;
        while (continueShopping) {
            serviceManager.getPromptInputMessageManager().printIntro();

            Order order = promptOrderUntilValidStock();

            List<PurchaseSummary> summaries = serviceManager.getPurchaseService().createPurchaseSummaries(order, now);
            updateSummariesWithAdditionalOptions(summaries);

            updateInventoryAndPrintReceipt(summaries, isMembershipDiscount());

            continueShopping = promptForAdditionalPurchase();
        }

        System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
    }

    private Order promptOrderUntilValidStock() {
        while (true) {
            try {
                return getOrderFromUserInput();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order getOrderFromUserInput() {
        String input = serviceManager.getPromptInputMessageManager().getUserResponseOrder();
        return serviceManager.getOrderService().createOrder(input, now);
    }

    private void updateSummariesWithAdditionalOptions(List<PurchaseSummary> summaries) {
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
                String eligibleFreeItemsYorN = PromptMessageManager.promptFreeItemOffer(product.getName());
                summaries.stream()
                        .filter(p -> p.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateEligibleFreeItemsWithOrderOption(eligibleFreeItemsYorN.equals("Y")));
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
                String nonDiscountedPurchaseYorN = PromptMessageManager.promptNonDiscountedPurchase(product.getName(), quantity);
                summaries.stream()
                        .filter(p -> p.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateNonDiscountedQuantityWithOrderOption(nonDiscountedPurchaseYorN.equals("Y")));
            }
        });
    }

    private boolean isMembershipDiscount() {
        String membershipYorN = PromptMessageManager.promptMembershipDiscount();
        return membershipYorN.equals("Y");
    }

    private void updateInventoryAndPrintReceipt(List<PurchaseSummary> summaries, boolean isMembershipDiscount) {
        List<InventoryItem> allItems = serviceManager.getInventoryReadService().getAllInventoryItems();
        serviceManager.getInventoryUpdateManager().updateInventoryFromSummaries(allItems, summaries);

        Receipt receipt = serviceManager.getPurchaseService().createReceipt(summaries, USER_ID, isMembershipDiscount);
        serviceManager.getPromptInputMessageManager().printReceipt(receipt);
    }

    private boolean promptForAdditionalPurchase() {
        String additionalYorN = PromptMessageManager.promptForAdditionalPurchase();
        return additionalYorN.equals("Y");
    }

}
