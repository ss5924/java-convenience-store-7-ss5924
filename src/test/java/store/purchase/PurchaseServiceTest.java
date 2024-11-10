package store.purchase;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.order.Order;
import store.order.OrderItem;
import store.inventory.InventoryReadService;
import store.payment.PaymentFactory;
import store.product.Product;
import store.product.ProductService;
import store.promotion.Promotion;
import store.promotion.PromotionService;
import store.purchasesummary.PurchaseSummary;
import store.purchasesummary.PurchaseSummaryFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class PurchaseServiceTest {
    private PurchaseService purchaseService;
    private MembershipService membershipService;
    private PaymentFactory paymentFactory;
    private InventoryReadService inventoryReadService;
    private PurchaseSummaryFactory purchaseSummaryFactory;
    private Promotion promotion;
    private Product product;
    private LocalDateTime now = DateTimes.now();
    private Order order;

    @BeforeEach
    void setUp() {
        MembershipManager membershipManager = new MembershipManager();
        membershipService = new MembershipService(membershipManager);
        paymentFactory = new PaymentFactory();
        inventoryReadService = new InventoryReadService(new ProductService(), new PromotionService());
        purchaseSummaryFactory = new PurchaseSummaryFactory();
        purchaseService = new PurchaseService(membershipService, inventoryReadService, paymentFactory, purchaseSummaryFactory);
        product = new Product("콜라", 1000);
        promotion = new Promotion("2+1", 2, 1, now, now.plusDays(1));

        order = new Order();
    }

    @DisplayName("영수증을 출력한다. - gift 없음")
    @Test
    void createReceipt() {
        List<PurchaseSummary> purchaseSummaries = new ArrayList<>();
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 2, 10, 10);
        purchaseSummaries.add(purchaseSummary);

        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 2));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 2));

        Receipt receipt = purchaseService.createReceipt(purchaseSummaries, "userid", true);

        System.out.println(receipt);
    }

    @DisplayName("영수증을 출력한다. - gift 있음")
    @Test
    void createReceipt2() {
        List<PurchaseSummary> purchaseSummaries = new ArrayList<>();
        PurchaseSummary purchaseSummary = new PurchaseSummary(product, promotion, 3, 10, 10);
        purchaseSummaries.add(purchaseSummary);

        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 3));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 3));

        Receipt receipt = purchaseService.createReceipt(purchaseSummaries, "userid", true);

        System.out.println(receipt);
    }
}