package store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {

    @Test
    void 구매수량이_0이하면_예외가_발생한다() {
        assertThatThrownBy(() -> new OrderItem(new Product("상품명", 1000), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 구매상품이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new OrderItem(null, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}