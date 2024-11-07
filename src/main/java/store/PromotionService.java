package store;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionService extends AbstractFileReadService<Promotion> {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    public PromotionService(MarkdownFileReader markdownFileReader) {
        super(markdownFileReader);
    }

    public Promotion createPromotion(String promotionName) {
        if ("null".equals(promotionName)) {
            return null;
        }
        return getPromotion(promotionName);
    }

    public Promotion getPromotion(String promotionName) {
        return getAllPromotions().stream()
                .filter(promotion -> promotion.getName().equalsIgnoreCase(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다."));
    }

    public Promotion getPromotionWithinValidPeriod(String promotionName) {
        Promotion promotion = getPromotion(promotionName);
        LocalDateTime now = DateTimes.now();

        if (isNotValidPeriod(promotion)) {
            throw new IllegalArgumentException("[ERROR] 프로모션 진행 기간이 아닙니다.");
        }
        return promotion;
    }

    public boolean isNotValidPeriod(Promotion promotion) {
        LocalDateTime now = DateTimes.now();
        return now.isBefore(promotion.getStartAt()) || now.isAfter(promotion.getEndAt());
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
