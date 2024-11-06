package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InventoryTest {
    private Product product;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        product = new Product("상품명", 1000);
        promotion = new Promotion("프로모션명", 2, 1, DateTimes.now(), DateTimes.now().plusDays(1));
    }

    @Test
    void 재고수량이_0개_미만이면_예외가_발생한다() {
        assertDoesNotThrow(() -> new Inventory(product, 0, promotion));

        assertThatThrownBy(() -> new Inventory(product, -1, promotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Inventory(null, 0, promotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션은_null이_가능하다() {
        assertDoesNotThrow(() -> new Inventory(product, 0, null));
    }

}