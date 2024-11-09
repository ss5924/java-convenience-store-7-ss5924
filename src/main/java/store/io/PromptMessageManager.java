package store.io;

import camp.nextstep.edu.missionutils.Console;
import store.product.InventoryItem;
import store.product.InventoryReadService;
import store.purchase.Receipt;

import java.util.List;

public class PromptMessageManager {
    private final InventoryReadService inventoryReadService;

    public PromptMessageManager(InventoryReadService inventoryReadService) {
        this.inventoryReadService = inventoryReadService;
    }

    public String getUserResponseOrder() {
        String input;
        while (true) {
            printIntro();
            input = Console.readLine().trim().toUpperCase();
            if (InputValidator.isValidFormat(input)) {
                return input;
            }
            System.out.println("[ERROR] 잘못된 입력입니다.");
        }
    }

    public static String getUserResponseYN(String message) {
        String input;
        while (true) {
            System.out.print(message + " (Y/N): ");
            input = Console.readLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                return input;
            }
            System.out.println("[ERROR] 잘못된 입력입니다. Y 또는 N만 입력 가능합니다.");
        }
    }

    public static String promptNonDiscountedPurchase(String productName, int quantity) {
        String message = String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?", productName, quantity);
        return getUserResponseYN(message);
    }

    public static String promptFreeItemOffer(String productName) {
        String message = String.format("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?", productName);
        return getUserResponseYN(message);
    }

    public static String promptMembershipDiscount() {
        String message = "멤버십 할인을 받으시겠습니까?";
        return getUserResponseYN(message);
    }

    public static String promptForAdditionalPurchase() {
        String message = "감사합니다. 구매하고 싶은 다른 상품이 있나요?";
        return getUserResponseYN(message);
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
