package store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.ServiceManager;
import store.input.PromptInputMessageManager;
import store.order.Order;
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

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        boolean continueShopping = true;
        while (continueShopping) {
            serviceManager.getOutputMessageManager().printIntro();

            Order order = getOrderFromUserInput();
            List<PurchaseSummary> summaries = serviceManager.getPurchaseService().createPurchaseSummaries(order, now);
            updateSummariesWithAdditionalOptions(summaries);

            updateInventoryAndPrintReceipt(summaries, isMembershipDiscount());

            continueShopping = promptForAdditionalPurchase();
        }

        System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
    }

    private Order getOrderFromUserInput() {
        String input = serviceManager.getPromptInputMessageManager().getUserResponseOrder();
        return serviceManager.getInputToOrderConverter().convertToOrder(input);
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
                String eligibleFreeItemsYorN = PromptInputMessageManager.promptFreeItemOffer(product.getName());
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
                String nonDiscountedPurchaseYorN = PromptInputMessageManager.promptNonDiscountedPurchase(product.getName(), quantity);
                summaries.stream()
                        .filter(p -> p.getProduct().equals(product))
                        .findFirst()
                        .ifPresent(summary -> summary.updateNonDiscountedQuantityWithOrderOption(nonDiscountedPurchaseYorN.equals("Y")));
            }
        });
    }

    private boolean isMembershipDiscount() {
        String membershipYorN = PromptInputMessageManager.promptMembershipDiscount();
        return membershipYorN.equals("Y");
    }

    private void updateInventoryAndPrintReceipt(List<PurchaseSummary> summaries, boolean isMembershipDiscount) {
        // TODO: 재고 업데이트 로직


        Receipt receipt = serviceManager.getPurchaseService().createReceipt(summaries, isMembershipDiscount);
        serviceManager.getOutputMessageManager().printReceipt(receipt);
    }

    private boolean promptForAdditionalPurchase() {
        String additionalYorN = PromptInputMessageManager.promptForAdditionalPurchase();
        return additionalYorN.equals("Y");
    }

}
