package store.purchase;

import store.product.Product;

public interface Item {
    Product getProduct();

    int getQuantity();

    int getAmount();

    int getDiscountAmount();
}
