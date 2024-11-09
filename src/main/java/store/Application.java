package store;

import store.common.ServiceManager;
import store.io.PromptHandler;
import store.order.Order;
import store.order.OrderProcessor;
import store.product.InventoryProcessor;
import store.purchase.PurchaseSummary;
import store.purchase.PurchaseSummaryProcessor;

import java.util.List;

public class Application {
    private static final String USER_ID = "SAMPLE_USER_ID_1";

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        ServiceManager serviceManager = new ServiceManager();

        PromptHandler promptHandler = serviceManager.getPromptHandler();
        OrderProcessor orderProcessor = new OrderProcessor(serviceManager.getOrderService(), promptHandler);
        PurchaseSummaryProcessor purchaseSummaryProcessor = new PurchaseSummaryProcessor(promptHandler);
        InventoryProcessor inventoryProcessor = new InventoryProcessor(
                serviceManager.getInventoryReadService(),
                serviceManager.getInventoryUpdateManager(),
                serviceManager.getPurchaseService(),
                promptHandler
        );

        boolean continueShopping = true;
        while (continueShopping) {
            promptHandler.printIntro();

            Order order = orderProcessor.promptOrderUntilValidStock();
            List<PurchaseSummary> summaries = serviceManager.getPurchaseService().createPurchaseSummaries(order);

            purchaseSummaryProcessor.updateSummariesWithAdditionalOptions(summaries);
            inventoryProcessor.updateInventoryAndPrintReceipt(summaries, USER_ID, isMembershipDiscount(promptHandler));

            continueShopping = PromptHandler.promptForAdditionalPurchase().equals("Y");
        }

        System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
    }

    private boolean isMembershipDiscount(PromptHandler promptHandler) {
        return PromptHandler.promptMembershipDiscount().equals("Y");
    }
}
