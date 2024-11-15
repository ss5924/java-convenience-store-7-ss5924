package store.purchase;

import store.product.Product;

public class PurchaseItem implements Item {
    private Product product;
    private int quantity;

    public PurchaseItem(Product product, int quantity) {
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

    @Override
    public int getAmount() {
        return this.product.getPrice() * this.quantity;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int getDiscountAmount() {
        throw new UnsupportedOperationException("[ERROR] 지원하지 않는 함수입니다.");
    }
}
