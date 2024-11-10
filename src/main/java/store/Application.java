package store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.ServiceManager;
import store.io.PromptHandler;
import store.order.OptionalOrderingProcessor;
import store.order.Order;
import store.order.OrderProcessor;
import store.inventory.InventoryProcessor;
import store.purchase.PurchaseProcessor;
import store.purchasesummary.PurchaseSummary;
import store.purchase.Receipt;

import java.util.List;

public class Application {
    private static final String USER_ID = "SAMPLE_USER_ID_1";

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        ServiceManager serviceManager = new ServiceManager();

        PromptHandler promptHandler = serviceManager.getPromptHandler();
        OrderProcessor orderProcessor = new OrderProcessor(promptHandler);
        OptionalOrderingProcessor optionalOrderingProcessor = new OptionalOrderingProcessor();
        InventoryProcessor inventoryProcessor = new InventoryProcessor(
                serviceManager.getInventoryReadService(), serviceManager.getInventoryUpdateManager());
        PurchaseProcessor purchaseProcessor = new PurchaseProcessor(serviceManager.getPurchaseService());

        promptHandler.printIntro();

        boolean continueShopping = true;
        while (continueShopping) {
            Order order = orderProcessor.promptOrderUntilValidInputForm(DateTimes.now());

            List<PurchaseSummary> summaries = serviceManager.getPurchaseService().createPurchaseSummaries(order);

            optionalOrderingProcessor.updateSummariesWithOptionalOrdering(summaries);
            inventoryProcessor.updateInventory(summaries);
            Receipt receipt = purchaseProcessor.processPurchase(summaries, USER_ID, order.isMembershipDiscount());

            promptHandler.printReceipt(receipt);

            continueShopping = PromptHandler.promptForAdditionalPurchase().equals("Y");
        }

        System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
    }

}
