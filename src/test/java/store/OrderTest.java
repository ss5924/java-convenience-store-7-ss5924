package store;

import org.junit.jupiter.api.Test;
import store.order.Order;
import store.order.OrderItem;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderTest {

    @Test
    void 주문상품이_null이면_예외가_발생한다() {
        Order order = new Order();

        assertDoesNotThrow(() -> order.addOrderItems(new OrderItem("상품명", 1)));

        assertThatThrownBy(() -> order.addOrderItems(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}