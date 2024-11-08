package store.purchase;

public interface Item {
    String getProductName();

    int getQuantity();

    int getAmount();

    int getDiscountAmount();
}
