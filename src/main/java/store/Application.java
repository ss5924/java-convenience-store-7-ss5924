package store;

import store.common.ProcessManager;
import store.common.ServiceManager;
import store.io.PromptHandler;
import store.order.Order;
import store.purchase.Receipt;
import store.purchasesummary.PurchaseSummary;

import java.util.List;

public class Application {
    private static final String USER_ID = "SAMPLE_USER_ID_1";

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        ServiceManager serviceManager = new ServiceManager();
        ProcessManager processManager = new ProcessManager(serviceManager);

        serviceManager.getPromptHandler().printIntro();

        boolean continueShopping = true;
        while (continueShopping) {
            continueShopping = processOrderCycle(serviceManager, processManager);
        }

        System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
    }

    private boolean processOrderCycle(ServiceManager serviceManager, ProcessManager processManager) {
        Order order = processManager.getOrderProcessor().promptOrderUntilValidInputForm();

        List<PurchaseSummary> summaries = serviceManager.getPurchaseService().createPurchaseSummaries(order);

        processManager.getOptionalOrderingProcessor().updateSummariesWithOptionalOrdering(summaries);
        processManager.getInventoryProcessor().updateInventory(summaries);
        Receipt receipt = processManager.getPurchaseProcessor().processPurchase(summaries, USER_ID, order.isMembershipDiscount());

        serviceManager.getPromptHandler().printReceipt(receipt);

        return PromptHandler.promptForAdditionalPurchase().equals("Y");
    }

}
