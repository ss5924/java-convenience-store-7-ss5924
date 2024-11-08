package store.product;

import store.common.AbstractFileReadService;
import store.promotion.Promotion;
import store.promotion.PromotionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryService extends AbstractFileReadService<InventoryItem> {
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private final ProductService productService;
    private final PromotionService promotionService;

    public InventoryService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public List<InventoryItem> getInventoryItems(String productName) {
        return getAllInventoryItems().stream()
                .filter(inventoryItem -> inventoryItem.getProduct().getName().equals(productName))
                .toList();
    }

    public int getCanApplyPromotionStockQuantity(Product product, LocalDateTime now) {
        return getPromotionStockItems(product, now).stream()
                .mapToInt(InventoryItem::getQuantity).sum();
    }

    public List<InventoryItem> getPromotionStockItems(Product product, LocalDateTime now) {
        return getInventoryItemsWithinValidPeriod(product, now).stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() != null).toList();
    }

    public int getCanApplyTotalQuantity(Product product, LocalDateTime now) {
        return getInventoryItemsWithinValidPeriod(product, now).stream()
                .mapToInt(InventoryItem::getQuantity).sum();
    }

    public List<InventoryItem> getInventoryItemsWithinValidPeriod(Product product, LocalDateTime now) {
        return getInventoryItemsWithinValidPeriod(now).stream()
                .filter(inventoryItem -> inventoryItem.getProduct().equals(product))
                .toList();
    }

    public List<InventoryItem> getInventoryItems(List<String> productNames) {
        List<InventoryItem> inventoryItems = getAllInventoryItems();
        List<InventoryItem> result = new ArrayList<>();
        productNames.forEach(name -> {
            result.addAll(inventoryItems.stream()
                    .filter(inventoryItem -> inventoryItem.getProduct().getName().equals(name))
                    .toList());
        });
        return result;
    }

    public List<InventoryItem> getInventoryItemsWithinValidPeriod(LocalDateTime now) {
        return getAllInventoryItems().stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() == null
                        || inventoryItem.getPromotion().isValidPeriod(now)).toList();
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
