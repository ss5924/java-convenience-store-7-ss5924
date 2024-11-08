package store.promotion;

import store.common.AbstractFileReadService;
import store.order.OrderItem;
import store.product.InventoryItem;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionService extends AbstractFileReadService<Promotion> {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    public Promotion getPromotion(String promotionName) {
        if ("null".equals(promotionName)) {
            return null;
        }
        return getPromotionByName(promotionName);
    }

    private Promotion getPromotionByName(String promotionName) {
        return getAllPromotions().stream()
                .filter(promotion -> promotion.getName().equalsIgnoreCase(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다."));
    }

    public List<Promotion> getAllPromotionsWithinValidPeriod(LocalDateTime now) {
        return getAllPromotions().stream().filter(promotion -> promotion.isValidPeriod(now)).toList();
    }

    public List<Promotion> getAllPromotions() {
        return getAllObjects(PROMOTION_FILE_PATH);
    }

    @Override
    protected Promotion mapToObject(List<String> line) {
        String name = line.get(0);
        int requiredCondition = parseInt(line.get(1));
        int giftQuantity = parseInt(line.get(2));
        LocalDateTime startAt = parseLocalDateTime(line.get(3));
        LocalDateTime endAt = parseLocalDateTime(line.get(4));
        return new Promotion(name, requiredCondition, giftQuantity, startAt, endAt);
    }

}
