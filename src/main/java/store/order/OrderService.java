package store.order;

import store.io.InputToOrderConverter;
import store.inventory.InventoryReadService;

import java.time.LocalDateTime;

public class OrderService {
    private final InventoryReadService inventoryReadService;
    private final InputToOrderConverter inputToOrderConverter;

    public OrderService(InventoryReadService inventoryReadService, InputToOrderConverter inputToOrderConverter) {
        this.inventoryReadService = inventoryReadService;
        this.inputToOrderConverter = inputToOrderConverter;
    }

    public Order createOrder(String input, LocalDateTime now) {
        Order order = inputToOrderConverter.convertToOrder(input);
        order.getOrderItems().forEach(orderItem -> {
            int availableStock = inventoryReadService.getStockByProduct(orderItem.getProduct(), now);
            validateStockAvailability(orderItem.getQuantity(), availableStock);
        });
        return order;
    }

    public void validateStockAvailability(int requestedQuantity, int availableStock) {
        if (requestedQuantity > availableStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}