package store.ui;

import store.inventory.InventoryItem;
import store.inventory.InventoryReadService;
import store.io.InputValidator;
import store.order.Order;
import store.order.OrderService;
import store.purchase.Receipt;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicUIHandler extends UserInterfaceHandler {
    private JFrame frame;

    public GraphicUIHandler(InventoryReadService inventoryReadService, OrderService orderService) {
        super(inventoryReadService, orderService);
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("W Convenience Store");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void closeAllWindows() {
        if (frame != null) {
            SwingUtilities.invokeLater(() -> {
                frame.dispose();
                System.out.println("GUI 창이 정상적으로 닫혔습니다.");
            });
        }
    }

    @Override
    public Order promptOrderUntilValidInputForm() {
        while (true) {
            try {
                String input = getUserResponseInputString();

                Order order = getOrderService().createOrder(input);
                boolean isMembershipDiscount = isMembershipDiscount();
                order.setMembershipDiscount(isMembershipDiscount);
                return order;

            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private boolean isMembershipDiscount() {
        return promptMembershipDiscount().equals("Y");
    }

    private String getUserResponseInputString() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea inventoryArea = new JTextArea(10, 40);
        inventoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inventoryArea);

        printAllInventoryItemsToArea(inventoryArea);

        JTextField inputField = new JTextField();
        JLabel instructionLabel = new JLabel("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(instructionLabel, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.SOUTH);

        while (true) {
            int result = JOptionPane.showConfirmDialog(frame, panel, "Enter your order", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String input = inputField.getText().trim().toUpperCase();
                if (InputValidator.isValidFormat(input)) {
                    return input;
                } else {
                    JOptionPane.showMessageDialog(frame, "[ERROR] 잘못된 입력입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (result == JOptionPane.CLOSED_OPTION) {
                throw new ApplicationExitException("[EXIT] 사용자가 프로그램을 종료했습니다.");
            }
        }
    }

    @Override
    public void printIntro() {
        JOptionPane.showMessageDialog(frame, "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void printReceipt(Receipt receipt) {
        String formattedReceipt = receipt.toString().replace("\t", "    ");
        JTextArea receiptArea = new JTextArea(formattedReceipt);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        JOptionPane.showMessageDialog(frame, scrollPane, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    private void printAllInventoryItemsToArea(JTextArea inventoryArea) {
        List<InventoryItem> allItems = getInventoryReadService().getAllInventoryItems();
        StringBuilder inventoryText = new StringBuilder();

        allItems.forEach(item -> inventoryText.append(item).append("\n"));
        inventoryArea.setText(inventoryText.toString());
    }

    @Override
    public String promptFreeItemOffer(String productName) {
        int result = JOptionPane.showConfirmDialog(
                null,
                String.format("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?", productName),
                "Free Item Offer",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            throw new ApplicationExitException("[EXIT] 사용자가 프로그램을 종료했습니다.");
        }

        return result == JOptionPane.YES_OPTION ? "Y" : "N";
    }

    @Override
    public String promptNonDiscountedPurchase(String productName, int quantity) {
        int result = JOptionPane.showConfirmDialog(
                null,
                String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?", productName, quantity),
                "Non-Discounted Purchase",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            throw new ApplicationExitException("[EXIT] 사용자가 프로그램을 종료했습니다.");
        }
        return result == JOptionPane.YES_OPTION ? "Y" : "N";
    }

    @Override
    public String promptMembershipDiscount() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "멤버십 할인을 받으시겠습니까?",
                "Membership Discount",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            throw new ApplicationExitException("[EXIT] 사용자가 프로그램을 종료했습니다.");
        }
        return result == JOptionPane.YES_OPTION ? "Y" : "N";
    }

    @Override
    public String promptForAdditionalPurchase() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "추가로 구매하시겠습니까?",
                "Additional Purchase",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            throw new ApplicationExitException("[EXIT] 사용자가 프로그램을 종료했습니다.");
        }
        return result == JOptionPane.YES_OPTION ? "Y" : "N";
    }

}
