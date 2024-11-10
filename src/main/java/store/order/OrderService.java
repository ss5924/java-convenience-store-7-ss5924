package store.order;

import store.inventory.InventoryReadService;
import store.io.InputToOrderConverter;

public class OrderService {
    private final InventoryReadService inventoryReadService;
    private final InputToOrderConverter inputToOrderConverter;

    public OrderService(InventoryReadService inventoryReadService, InputToOrderConverter inputToOrderConverter) {
        this.inventoryReadService = inventoryReadService;
        this.inputToOrderConverter = inputToOrderConverter;
    }

    public Order createOrder(String input) {
        Order order = inputToOrderConverter.convertToOrder(input);
        order.getOrderItems().forEach(orderItem -> {
            int availableStock = inventoryReadService.getStockByProduct(orderItem.getProduct());
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