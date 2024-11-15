package store.inventory;

import store.product.Product;
import store.promotion.Promotion;

import java.text.NumberFormat;

public class InventoryItem {
    private Product product;
    private int quantity;
    private Promotion promotion;

    public InventoryItem(Product product, int quantity, Promotion promotion) {
        validate(product, quantity);
        this.product = product;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    private void validate(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 주문 물품은 null이 될 수 없습니다.");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 재고 개수는 음수가 될 수 없습니다. quantity=" + quantity);
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        NumberFormat numberFormat = NumberFormat.getInstance();
        sb.append("- ").append(product.getName()).append(" ").append(numberFormat.format(product.getPrice())).append("원 ");
        if (quantity > 0) {
            sb.append(quantity).append("개");
        } else {
            sb.append("재고 없음");
        }
        if (promotion != null) {
            sb.append(" ").append(promotion.getName());
        }
        return sb.toString();
    }
}
