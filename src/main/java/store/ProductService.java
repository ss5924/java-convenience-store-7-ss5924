package store;

public class ProductService {
    public Product createProduct(String productName, int price) {
        return new Product(productName, price);
    }
}
