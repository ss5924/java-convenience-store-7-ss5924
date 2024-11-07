package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @DisplayName("상품명과 가격으로 파일에서 상품 정보를 가져온다.")
    @Test
    void getProduct() {
        Product product = productService.getProduct("콜라", 1000);

        assertEquals("콜라", product.getName());
        assertEquals(1000, product.getPrice());
    }

    @DisplayName("전체 상품을 중복 제외하여 가져온다.")
    @Test
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertEquals(11, products.size());
        assertEquals("콜라", products.get(0).getName());
        assertEquals("사이다", products.get(1).getName());
        assertEquals("오렌지주스", products.get(2).getName());
        assertEquals("컵라면", products.getLast().getName());
    }

}