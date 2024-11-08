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

    public int getStockAvailableWithPromotionByProductAndPromotion(Product product, Promotion promotion, LocalDateTime now, int orderedQuantity) {
        boolean isAvailableForGift = promotion.isAvailableForGift(orderedQuantity);
        if (!isAvailableForGift) {
            return 0;
        }
        return getInventoryItemsWithPromotionByProductAndPromotion(product, promotion, now).getQuantity();
    }

    public InventoryItem getInventoryItemsWithPromotionByProductAndPromotion(Product product, Promotion promotion, LocalDateTime now) {
        InventoryItem itemWithPromotion = getInventoryItemWithPromotionByProduct(product, now);
        if (!itemWithPromotion.getPromotion().equals(promotion)) {
            throw new IllegalArgumentException("[ERROR] 조건에 맞는 재고가 없습니다.");
        }
        return itemWithPromotion;
    }

    public List<InventoryItem> getInventoryItemsByProduct(Product product, LocalDateTime now) {
        InventoryItem itemWithoutPromotion = getInventoryItemsWithoutPromotionByProduct(product);
        InventoryItem itemWithPromotion = getInventoryItemWithPromotionByProduct(product, now);

        List<InventoryItem> result = new ArrayList<>();
        result.add(itemWithPromotion);
        result.add(itemWithoutPromotion);

        return result;
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

    public List<InventoryItem> getInventoryItemsWithinValidPeriod(LocalDateTime now) {
        List<InventoryItem> result = new ArrayList<>();
        result.addAll(getInventoryItemsWithPromotion(now));
        result.addAll(getInventoryItemsWithoutPromotion());
        return result;
    }

    public List<InventoryItem> getInventoryItemsWithoutPromotion() {
        return getAllInventoryItems().stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() == null).toList();
    }

    public List<InventoryItem> getInventoryItemsWithPromotion(LocalDateTime now) {
        return getAllInventoryItems().stream()
                .filter(inventoryItem -> inventoryItem.getPromotion() != null
                        && inventoryItem.getPromotion().isValidPeriod(now)).toList();
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
