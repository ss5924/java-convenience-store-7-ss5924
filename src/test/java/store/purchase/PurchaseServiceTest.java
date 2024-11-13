package store.purchase;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.inventory.InventoryReadService;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.order.Order;
import store.order.OrderItem;
import store.payment.PaymentFactory;
import store.product.Product;
import store.product.ProductService;
import store.promotion.Promotion;
import store.promotion.PromotionService;
import store.purchasesummary.PurchaseSummary;
import store.purchasesummary.PurchaseSummaryFactory;

import java.time.LocalDateTime;
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
        purchaseService = new PurchaseService(membershipService, paymentFactory);
        product = new Product("콜라", 1000);
        promotion = new Promotion("2+1", 2, 1, now, now.plusDays(1));
        order = new Order();
    }

    @DisplayName("영수증을 출력한다. - gift 없음")
    @Test
    void createReceipt() {
        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 2));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 2));

        List<PurchaseSummary> summaries = purchaseSummaryFactory.createPurchaseSummaries(order, inventoryReadService);

        Receipt receipt = purchaseService.createReceipt(summaries, "userid", true);

        System.out.println(receipt);
    }

    @DisplayName("영수증을 출력한다. - gift 있음")
    @Test
    void createReceipt2() {
        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 3));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 3));

        List<PurchaseSummary> summaries = purchaseSummaryFactory.createPurchaseSummaries(order, inventoryReadService);

        Receipt receipt = purchaseService.createReceipt(summaries, "userid", true);

        System.out.println(receipt);
    }
}