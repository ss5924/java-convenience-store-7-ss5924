package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}