package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.Product;
import store.purchase.GiftItem;
import store.purchase.Item;
import store.payment.Payment;
import store.purchase.PurchaseItem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {
    private List<Item> purchaseItems;
    private List<Item> giftItems;
    private Payment payment;

    @BeforeEach
    void setUp() {
        purchaseItems = new ArrayList<>();
        giftItems = new ArrayList<>();

        Product cola = new Product("cola", 1000);
        Product choco = new Product("choco", 2000);

        purchaseItems.add(new PurchaseItem(cola, 3));
        purchaseItems.add(new PurchaseItem(choco, 5));

        giftItems.add(new GiftItem(cola, 1));

        payment = new Payment(purchaseItems, giftItems);
    }

    @Test
    void 총구매액을_계산한다() {
        assertEquals(13000, payment.getTotalPurchaseAmount());
    }

    @Test
    void 프로모션_행사_할인을_계산한다() {
        assertEquals(1000, payment.getPromotionDiscountAmount());
    }

    @Test
    void 멤버십_할인_금액을_계산한다() {
        assertEquals(3600, payment.getMembershipDiscountAmount());
    }

    @Test
    void 최종_지불_금액을_계산한다() {
        assertEquals(8400, payment.getFinalPaymentAmount());
    }
}