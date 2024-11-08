package store.purchase;

import store.product.Product;

public class GiftItem implements Item {
    private Product product;
    private int quantity;

    public GiftItem(Product product, int quantity) {
        validate(product, quantity);
        this.product = product;
        this.quantity = quantity;
    }

    private void validate(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 주문 물품은 null이 될 수 없습니다.");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 0이상이어야 합니다.");
        }
    }

    public int getDiscountAmount() {
        return this.product.getPrice() * this.quantity;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public String getProductName() {
        return this.product.getName();
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }
}
