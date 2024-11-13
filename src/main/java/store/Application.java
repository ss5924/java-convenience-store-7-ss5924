package store;

import store.common.ProcessManager;
import store.common.ServiceManager;
import store.order.Order;
import store.purchase.Receipt;
import store.purchasesummary.PurchaseSummary;
import store.ui.ApplicationExitException;

import java.util.List;

public class Application {
    private static final String USER_ID = "SAMPLE_USER_ID_1";

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        ServiceManager serviceManager = new ServiceManager();
        ProcessManager processManager = new ProcessManager(serviceManager);

        boolean continueShopping = true;
        try {
            while (continueShopping) {
                continueShopping = processOrderCycle(serviceManager, processManager);
            }
        } catch (ApplicationExitException e) {
            System.out.println("사용자가 프로그램을 종료하였습니다.");
        } finally {
            shutdownApplication(serviceManager);
            System.out.println("감사합니다! W편의점을 이용해 주셔서 감사합니다.");
        }
    }

    private boolean processOrderCycle(ServiceManager serviceManager, ProcessManager processManager) {
        try {
            Order order = processManager.getOrderProcessor().promptOrderUntilValidInputForm();

            List<PurchaseSummary> summaries = processManager.getPurchaseSummaryProcessor().createPurchaseSummaries(order);
            processManager.getOptionalOrderingProcessor().updateSummariesWithOptionalOrdering(summaries);
            processManager.getInventoryProcessor().updateInventory(summaries);
            Receipt receipt = processManager.getPurchaseProcessor().processPurchase(summaries, USER_ID, order.isMembershipDiscount());
            serviceManager.getGraphicUIHandler().printReceipt(receipt);

            return serviceManager.getGraphicUIHandler().promptForAdditionalPurchase().equals("Y");
        } catch (ApplicationExitException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("예기치 못한 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    private void shutdownApplication(ServiceManager serviceManager) {
        serviceManager.getGraphicUIHandler().closeAllWindows();
        System.out.println("모든 리소스를 정리하고 프로그램을 종료합니다.");
    }

}
