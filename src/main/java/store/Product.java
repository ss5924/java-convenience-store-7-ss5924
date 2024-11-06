package store;

public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        validate(price);
        this.name = name;
        this.price = price;
    }

    private void validate(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 0원 이상부터 가능합니다.");
        }
    }

}
