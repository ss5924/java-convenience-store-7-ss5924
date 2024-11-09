package store.io;

import store.order.Order;
import store.order.OrderItem;
import store.product.Product;
import store.product.ProductService;

import java.util.Arrays;

public class InputToOrderConverter {
    private final ProductService productService;

    public InputToOrderConverter(ProductService productService) {
        this.productService = productService;
    }

    public Order convertToOrder(String input) {
        String trimmedInput = input.replaceAll("\\s+", "");
        InputValidator.validateInputFormat(trimmedInput);

        Order order = new Order();
        String[] itemStrings = convertToArray(trimmedInput);

        Arrays.stream(itemStrings)
                .map(this::convertToOrderItem)
                .forEach(order::addOrderItems);

        return order;
    }

    private String[] convertToArray(String input) {
        return input.replaceAll("[\\[\\]]", "").split(",");
    }

    private OrderItem convertToOrderItem(String oneItem) {
        String[] itemArr = oneItem.split("-");
        String productName = itemArr[0];

        Product product = productService.getProduct(productName);
        int quantity = parseIntQuantity(itemArr[1]);

        return new OrderItem(product, quantity);
    }

    private int parseIntQuantity(String quantityStr) {
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 갯수를 다시 입력해주세요.");
        }
        return quantity;
    }

}
