package store.input;

import camp.nextstep.edu.missionutils.DateTimes;
import store.order.Order;
import store.product.InventoryItem;
import store.product.InventoryReadService;
import store.purchase.PurchaseService;
import store.purchase.Receipt;

import java.time.LocalDateTime;
import java.util.List;

public class OutputMessageManager {
    private final PurchaseService purchaseService;
    private final InventoryReadService inventoryReadService;

    public OutputMessageManager(PurchaseService purchaseService, InventoryReadService inventoryReadService) {
        this.purchaseService = purchaseService;
        this.inventoryReadService = inventoryReadService;
    }

    public void printIntro() {
        System.out.println("안녕하세요. W편의점입니다.\n" + "현재 보유하고 있는 상품입니다.\n");
        printAllInventoryStocks();
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }

    public void printAllInventoryStocks() {
        List<InventoryItem> allItems = inventoryReadService.getAllInventoryItems();

        allItems.forEach(System.out::println);
    }

    public void printReceipt(Receipt receipt) {
        System.out.println(receipt);
    }

}
