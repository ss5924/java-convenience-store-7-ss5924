package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.Product;
import store.purchase.GiftItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftItemTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("상품명", 1000);
    }

    @Test
    void 증정품의_지불가격은_0원이다() {
        assertEquals(0, new GiftItem(product, 1).getAmount());
    }

}