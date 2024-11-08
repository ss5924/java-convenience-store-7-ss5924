package store.order;

import store.product.ProductService;
import store.util.InputValidator;

import java.util.Arrays;

public class OrderInputParser {
    private ProductService productService;

    public OrderInputParser(ProductService productService) {
        this.productService = productService;
    }

    public Order parseToOrder(String input) {
        InputValidator.validateInputFormat(input);

        Order order = new Order();
        String[] itemStrings = parseItems(input);

        Arrays.stream(itemStrings)
                .map(this::parseToOrderItem)
                .forEach(order::addOrderItems);

        return order;
    }

    private String[] parseItems(String input) {
        return input.replaceAll("[\\[\\]]", "").split(",");
    }

    private OrderItem parseToOrderItem(String itemString) {
        String[] itemParts = itemString.split("-");
        String productName = itemParts[0];
        int quantity = parseIntQuantity(itemParts[1]);

        return new OrderItem(productName, quantity);
    }

    private int parseIntQuantity(String quantityStr) {
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 갯수를 다시 입력해주세요.");
        }
        return quantity;
    }

}
