package store.input;

import camp.nextstep.edu.missionutils.Console;

public class PromptInputMessageManager {
    private final OutputMessageManager outputMessageManager;

    public PromptInputMessageManager(OutputMessageManager outputMessageManager) {
        this.outputMessageManager = outputMessageManager;
    }

    public String getUserResponseOrder() {
        String input;
        while (true) {
            outputMessageManager.printIntro();
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

}
