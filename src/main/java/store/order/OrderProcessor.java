package store.order;

import store.io.PromptHandler;

public class OrderProcessor {
    private final OrderService orderService;
    private final PromptHandler promptHandler;

    public OrderProcessor(OrderService orderService, PromptHandler promptHandler) {
        this.orderService = orderService;
        this.promptHandler = promptHandler;
    }

    public Order promptOrderUntilValidStock() {
        while (true) {
            try {
                return getOrderFromUserInput();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order getOrderFromUserInput() {
        String input = promptHandler.getUserResponseOrder();
        return orderService.createOrder(input);
    }
}