package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputParserTest {
    private InputParser inputParser;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductService();
        inputParser = new InputParser(productService);
    }

    @Test
    void 주문갯수가_0일때_예외가_발생한다() {
        assertThatThrownBy(() -> inputParser.parseToOrder("[콜라-10],[사이다-0]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력이_주어졌을때_주문상품내역이_맞는지_확인한다() {
        Order order = inputParser.parseToOrder("[콜라-10],[사이다-1]");

        assertEquals(2, order.getOrderItems().size());
        assertEquals("콜라", order.getOrderItems().getFirst().getProduct().getName());
        assertEquals("사이다", order.getOrderItems().getLast().getProduct().getName());
        assertEquals(10, order.getOrderItems().getFirst().getQuantity());
        assertEquals(1, order.getOrderItems().getLast().getQuantity());
    }
}