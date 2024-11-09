package store.output;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.input.InputToOrderConverter;
import store.input.OutputMessageManager;
import store.input.PromptInputMessageManager;
import store.membership.MembershipManager;
import store.membership.MembershipService;
import store.order.Order;
import store.order.OrderItem;
import store.product.InventoryReadService;
import store.product.Product;
import store.product.ProductService;
import store.promotion.PromotionService;
import store.purchase.PaymentFactory;
import store.purchase.PurchaseService;
import store.purchase.PurchaseSummaryFactory;

import java.time.LocalDateTime;

class OutputMessageManagerTest {
    private OutputMessageManager outputMessageManager;

//    @BeforeEach
    void setUp() {
        MembershipManager membershipManager = new MembershipManager();
        MembershipService membershipService = new MembershipService(membershipManager);
        PaymentFactory paymentFactory = new PaymentFactory();
        InventoryReadService inventoryReadService = new InventoryReadService(new ProductService(), new PromotionService());
        PurchaseSummaryFactory purchaseSummaryFactory = new PurchaseSummaryFactory();
        PurchaseService purchaseService = new PurchaseService(membershipService, inventoryReadService, paymentFactory, purchaseSummaryFactory);

        outputMessageManager = new OutputMessageManager(purchaseService, inventoryReadService);
    }

    @DisplayName("전체 재고를 출력한다.")
    @Test
    void printAllInventoryStocks() {
        outputMessageManager.printAllInventoryStocks();
    }

    @DisplayName("영수증을 출력한다.")
    @Test
    void printReceipt() {
        Order order = new Order();
        order.addOrderItems(new OrderItem(new Product("콜라", 1000), 2));
        order.addOrderItems(new OrderItem(new Product("사이다", 1000), 2));

//        outputMessageManager.printReceipt();
    }

    @DisplayName("인트로를 출력한다.")
    @Test
    void printIntro() {
        outputMessageManager.printIntro();
    }

    @DisplayName("전체 테스트")
    @Test
    void total() {
        MembershipManager membershipManager = new MembershipManager();
        ProductService productService = new ProductService();
        PromotionService promotionService = new PromotionService();
        MembershipService membershipService = new MembershipService(membershipManager);
        InventoryReadService inventoryReadService = new InventoryReadService(productService, promotionService);
        PaymentFactory paymentFactory = new PaymentFactory();
        PurchaseSummaryFactory purchaseSummaryFactory = new PurchaseSummaryFactory();
        PurchaseService purchaseService = new PurchaseService(membershipService, inventoryReadService, paymentFactory, purchaseSummaryFactory);
        OutputMessageManager outputMessageManager = new OutputMessageManager(purchaseService, inventoryReadService);
        PromptInputMessageManager promptInputMessageManager = new PromptInputMessageManager(outputMessageManager);
        InputToOrderConverter inputToOrderConverter = new InputToOrderConverter(productService);


        String input = "[콜라-3],[사이다-1]";//promptInputMessageManager.getUserResponseOrder();
        Order order = inputToOrderConverter.convertToOrder(input);


    }
}