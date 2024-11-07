package store;

import java.util.List;

public class ProductService extends AbstractFileReadService<Product> {
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    public Product getProduct(String productName) {
        return getProductByProductName(productName);
    }

    public Product getProduct(String productName, int price) {
        return getProductByProductNameAndPrice(productName, price);
    }

    private Product getProductByProductName(String productName) {
        return getAllProducts().stream().filter(product ->
                        product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }

    private Product getProductByProductNameAndPrice(String productName, int price) {
        return getAllProducts().stream().filter(product ->
                        product.getName().equals(productName) && product.getPrice() == price)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다."));
    }

    public List<Product> getAllProducts() {
        return getAllObjects(PRODUCT_FILE_PATH).stream().distinct().toList();
    }

    @Override
    protected Product mapToObject(List<String> line) {
        String productName = line.get(0);
        int price = parseInt(line.get(1));
        return new Product(productName, price);
    }

}
