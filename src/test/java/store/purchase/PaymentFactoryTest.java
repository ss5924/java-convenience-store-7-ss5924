package store.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.product.Product;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentFactoryTest {
    private PaymentFactory paymentFactory;
    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        paymentFactory = new PaymentFactory();
        MembershipManager membershipManager = new MembershipManager();
        membershipService = new MembershipService(membershipManager);
    }

    @DisplayName("결제 금액을 계산한다.")
    @Test
    void create() {
        List<Item> purchaseItems = new ArrayList<>();
        List<Item> giftItems = new ArrayList<>();

        Product product = new Product("콜라", 1000);
        purchaseItems.add(new PurchaseItem(product, 2));

        Payment payment = paymentFactory.create(purchaseItems, giftItems, membershipService, "1");

        assertEquals(2000, payment.getTotalPurchaseAmount());
        assertEquals(0, payment.getPromotionDiscountAmount());
        assertEquals(2000*0.3, payment.getMembershipDiscountAmount());
        assertEquals(2000-2000*0.3, payment.getFinalPaymentAmount());
    }

    @DisplayName("결제 금액을 계산한다.")
    @Test
    void create2() {
        List<Item> purchaseItems = new ArrayList<>();
        List<Item> giftItems = new ArrayList<>();

        Product product = new Product("콜라", 1000);
        purchaseItems.add(new PurchaseItem(product, 2));
        giftItems.add(new GiftItem(product, 1));

        Payment payment = paymentFactory.create(purchaseItems, giftItems, membershipService, "1");

        assertEquals(3000, payment.getTotalPurchaseAmount());
        assertEquals(1000, payment.getPromotionDiscountAmount());
        assertEquals(2000*0.3, payment.getMembershipDiscountAmount());
        assertEquals(2000-2000*0.3, payment.getFinalPaymentAmount());
    }
}