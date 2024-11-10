package store.order;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> orderItems;
    private boolean isMembershipDiscount;
    private final LocalDateTime registeredDate;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.isMembershipDiscount = false;
        this.registeredDate = DateTimes.now();
    }

    public void setMembershipDiscount(boolean membershipDiscount) {
        isMembershipDiscount = membershipDiscount;
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

    public boolean isMembershipDiscount() {
        return isMembershipDiscount;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }
}
