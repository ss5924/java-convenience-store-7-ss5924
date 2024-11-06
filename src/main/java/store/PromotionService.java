package store;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PromotionService {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private final MarkdownFileReader markdownFileReader;

    public PromotionService(MarkdownFileReader markdownFileReader) {
        this.markdownFileReader = markdownFileReader;
    }

    public Promotion getPromotionWithinValidPeriod(String promotionName) {
        Promotion promotion = getPromotion(promotionName);
        LocalDateTime now = DateTimes.now();

        if (now.isBefore(promotion.getStartAt()) || now.isAfter(promotion.getEndAt())) {
            throw new IllegalArgumentException("[ERROR] 프로모션 진행 기간이 아닙니다.");
        }
        return promotion;
    }

    public Promotion getPromotion(String promotionName) {
        return getAllPromotions().stream()
                .filter(promotion -> promotion.getName().equalsIgnoreCase(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다."));
    }

    public List<Promotion> getAllPromotions() {
        List<List<String>> promotionLines = markdownFileReader.readMarkdownFile(PROMOTION_FILE_PATH);
        List<Promotion> promotions = new ArrayList<>();

        for (List<String> line : promotionLines) {
            String name = line.get(0);
            int requiredCondition = parseInt(line.get(1));
            int giftQuantity = parseInt(line.get(2));
            LocalDateTime startAt = parseLocalDateTime(line.get(3));
            LocalDateTime endAt = parseLocalDateTime(line.get(4));

            Promotion promotion = setPromotion(name, requiredCondition, giftQuantity, startAt, endAt);
            promotions.add(promotion);
        }
        return promotions;
    }

    private Promotion setPromotion(String name, int requiredCondition, int giftQuantity, LocalDateTime startAt, LocalDateTime endAt) {
        return new Promotion(name, requiredCondition, giftQuantity, startAt, endAt);
    }

    private int parseInt(String element) {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요. element=" + element);
        }
    }

    private LocalDateTime parseLocalDateTime(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return localDate.atStartOfDay();
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 날짜 형식으로 입력해주세요. date=" + date);
        }
    }

}
