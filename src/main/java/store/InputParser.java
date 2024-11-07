package store;

import java.util.Arrays;

public class InputParser {
    private ProductService productService;

    public InputParser(ProductService productService) {
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
        Product product = productService.getProduct(productName);

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
