package store;

public class ProductService {
    public Product setProduct(String productName, int price) {
        return new Product(productName, price);
    }
}
