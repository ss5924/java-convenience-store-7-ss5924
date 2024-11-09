package store.order;

import store.product.Product;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        validate(product, quantity);
        this.product = product;
        this.quantity = quantity;
    }

    private void validate(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 0이상이어야 합니다.");
        }

        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 주문 물품은 null이 될 수 없습니다.");
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
