package store.purchase;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.product.Product;
import store.promotion.Promotion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseServiceTest {
    private PurchaseService purchaseService;
    private MembershipService membershipService;
    private PaymentFactory paymentFactory;
    private Promotion promotion;
    private Product product;
    private LocalDateTime now = DateTimes.now();

    @BeforeEach
    void setUp() {
        MembershipManager membershipManager = new MembershipManager();
        membershipService = new MembershipService(membershipManager);
        paymentFactory = new PaymentFactory();
        purchaseService = new PurchaseService(membershipService, paymentFactory);
        product = new Product("콜라", 1000);
        promotion = new Promotion("2+1", 2, 1, now, now.plusDays(1));
    }

    @DisplayName("영수증을 출력한다. - gift 없음")
    @Test
    void createReceipt() {
        List<PurchaseSummary> purchaseSummaries = new ArrayList<>();
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 2, 10, 10);
        purchaseSummaries.add(purchaseSummary);

        Receipt receipt = purchaseService.createReceipt(purchaseSummaries);

        System.out.println(receipt);
    }

    @DisplayName("영수증을 출력한다. - gift 있음")
    @Test
    void createReceipt2() {
        List<PurchaseSummary> purchaseSummaries = new ArrayList<>();
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 3, 10, 10);
        purchaseSummaries.add(purchaseSummary);

        Receipt receipt = purchaseService.createReceipt(purchaseSummaries);

        System.out.println(receipt);
    }
}