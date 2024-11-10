package store.inventory;

import store.common.AbstractFileReadService;
import store.product.Product;
import store.product.ProductService;
import store.promotion.Promotion;
import store.promotion.PromotionService;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryReadService extends AbstractFileReadService<InventoryItem> {
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private final ProductService productService;
    private final PromotionService promotionService;

    public InventoryReadService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public int getStockByProduct(Product product, LocalDateTime now) {
        return getInventoryItemsByProduct(product, now)
                .stream().mapToInt(InventoryItem::getQuantity).sum();
    }

    public List<InventoryItem> getInventoryItemsByProduct(Product product, LocalDateTime now) {
        return getAllInventoryItemsWithValidPeriod(now)
                .stream().filter(item -> item.getProduct().equals(product)).toList();
    }

    public InventoryItem getInventoryItemsWithoutPromotionByProduct(Product product) {
        return getInventoryItemsWithoutPromotion().stream()
                .filter(inventoryItem -> inventoryItem.getProduct().equals(product))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("[ERROR] 조건에 맞는 재고가 없습니다."));
    }

    public InventoryItem getInventoryItemWithPromotionByProduct(Product product, LocalDateTime now) {
        return getInventoryItemsWithPromotion(now).stream()
                .filter(inventoryItem -> inventoryItem.getProduct().equals(product))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("[ERROR] 조건에 맞는 재고가 없습니다."));
    }

    public List<InventoryItem> getInventoryItemsWithoutPromotion() {
        return getAllInventoryItems().stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() == null).toList();
    }

    public List<InventoryItem> getInventoryItemsWithPromotion(LocalDateTime now) {
        return getAllInventoryItemsWithValidPeriod(now).stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() != null).toList();
    }

    public List<InventoryItem> getAllInventoryItemsWithValidPeriod(LocalDateTime now) {
        return getAllInventoryItems().stream()
                .filter(item -> item.getPromotion() == null || item.getPromotion().isValidPeriod(now))
                .toList();
    }

    public List<InventoryItem> getAllInventoryItems() {
        return getAllObjects(PRODUCT_FILE_PATH);
    }

    @Override
    protected InventoryItem mapToObject(List<String> line) {
        String promotionName = line.get(3);
        Promotion promotion = promotionService.getPromotion(promotionName);

        String productName = line.get(0);
        int productPrice = parseInt(line.get(1));
        Product product = productService.getProduct(productName, productPrice);

        int quantity = parseInt(line.get(2));
        return new InventoryItem(product, quantity, promotion);
    }

}
