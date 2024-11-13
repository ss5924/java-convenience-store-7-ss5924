package store.ui;

import camp.nextstep.edu.missionutils.Console;
import store.inventory.InventoryItem;
import store.inventory.InventoryReadService;
import store.order.Order;
import store.order.OrderService;
import store.purchase.Receipt;

import java.util.List;

public class ConsoleUIHandler extends UserInterfaceHandler {

    public ConsoleUIHandler(InventoryReadService inventoryReadService, OrderService orderService) {
        super(inventoryReadService, orderService);
    }

    @Override
    public Order promptOrderUntilValidInputForm() {
        while (true) {
            try {
                printIntro();
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                String input = Console.readLine().trim().toUpperCase();

                Order order = getOrderService().createOrder(input);
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
                String discountInput = Console.readLine().trim().toUpperCase();
                order.setMembershipDiscount(discountInput.equals("Y"));
                return order;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void printIntro() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.");
        printAllInventoryStocks();
    }

    @Override
    public void printReceipt(Receipt receipt) {
        System.out.println(receipt);
    }

    private void printAllInventoryStocks() {
        List<InventoryItem> allItems = getInventoryReadService().getAllInventoryItems();
        allItems.forEach(System.out::println);
    }

    @Override
    public String promptFreeItemOffer(String productName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N): ", productName);
        return Console.readLine().trim().toUpperCase();
    }

    @Override
    public String promptNonDiscountedPurchase(String productName, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N): ", productName, quantity);
        return Console.readLine().trim().toUpperCase();
    }

    @Override
    public String promptMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine().trim().toUpperCase();
    }

    @Override
    public String promptForAdditionalPurchase() {
        System.out.println("추가로 구매하시겠습니까? (Y/N)");
        return Console.readLine().trim().toUpperCase();
    }

}
