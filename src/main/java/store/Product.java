package store;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
