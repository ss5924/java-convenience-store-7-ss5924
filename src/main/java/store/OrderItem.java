package store;

public class OrderItem {
    private String orderedProductName;
    private int quantity;

    public OrderItem(String orderedProductName, int quantity) {
        validate(orderedProductName, quantity);
        this.orderedProductName = orderedProductName;
        this.quantity = quantity;
    }

    private void validate(String orderedProductName, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 0이상이어야 합니다.");
        }

        if (orderedProductName == null) {
            throw new IllegalArgumentException("[ERROR] 주문 물품은 null이 될 수 없습니다.");
        }
    }

    public String getOrderedProductName() {
        return orderedProductName;
    }

    public int getQuantity() {
        return quantity;
    }
}
