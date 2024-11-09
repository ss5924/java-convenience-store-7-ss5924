package store.product;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.common.ServiceManager;
import store.order.Order;
import store.order.OrderItem;
import store.purchase.PurchaseSummary;

import java.util.List;

class InventoryUpdateManagerTest {
    private ServiceManager serviceManager;

    @BeforeEach
    void setUp() {
        serviceManager = new ServiceManager();
    }

    @DisplayName("재고를 파일에 업데이트한다.")
    @Test
    void updateInventoryFromSummaries() {
        java.util.List<InventoryItem> inventoryItems = serviceManager.getInventoryReadService().getAllInventoryItems();

        Order order = new Order();
        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 3));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 3));

        List<PurchaseSummary> summaries = serviceManager.getPurchaseSummaryFactory()
                .createPurchaseSummaries(order, serviceManager.getInventoryReadService(), DateTimes.now());

        serviceManager.getInventoryUpdateManager().updateInventoryFromSummaries(inventoryItems, summaries);
    }
}