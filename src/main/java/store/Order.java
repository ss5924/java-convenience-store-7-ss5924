package store;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public void addOrderItems(OrderItem orderItem) {
        validate(orderItem);
        this.orderItems.add(orderItem);
    }

    private void validate(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("[ERROR] 주문 물품은 null이 될 수 없습니다.");
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
