package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseItemTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("상품명", 1000);
    }

    @Test
    void 상품과_가격을_확인해_지불금액을_계산한다() {
        assertEquals(3000, new PurchaseItem(product, 3).getAmount());
    }

    @Test
    void 구매상품클래스는_할인금액함수를_호출하면_예외가_발생한다() {
        assertThatThrownBy(() -> new PurchaseItem(product, 3).getDiscountAmount())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}