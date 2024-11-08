package store.promotion;

import java.time.LocalDateTime;
import java.util.Objects;

public class Promotion {
    private final String name;
    private final int requiredCondition;
    private final int giftQuantity;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public Promotion(String name, int requiredCondition, int giftQuantity, LocalDateTime startAt, LocalDateTime endAt) {
        validate(requiredCondition, giftQuantity, startAt, endAt);
        this.name = name;
        this.requiredCondition = requiredCondition;
        this.giftQuantity = giftQuantity;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validate(int requiredCondition, int giftQuantity, LocalDateTime startAt, LocalDateTime endAt) {
        if (requiredCondition <= 0) {
            throw new IllegalArgumentException("[ERROR] 프로모션 참여 구매 개수 조건은 0을 초과해야 합니다.");
        }

        if (giftQuantity < 0) {
            throw new IllegalArgumentException("[ERROR] 프로모션 증정품 개수는 0이상이어야 합니다.");
        }

        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("[ERROR] 프로모션 종료일이 시작일보다 빠릅니다.");
        }
    }

    public boolean isValidPeriod(LocalDateTime now) {
        return this.startAt.isBefore(now) && this.endAt.isAfter(now);
    }

    public boolean isAvailableForGift(int orderedQuantity) {
        return orderedQuantity >= this.requiredCondition;
    }

    public String getName() {
        return name;
    }

    public int getRequiredCondition() {
        return requiredCondition;
    }

    public int getGiftQuantity() {
        return giftQuantity;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Promotion promotion = (Promotion) o;
        return requiredCondition == promotion.requiredCondition
                && giftQuantity == promotion.giftQuantity
                && Objects.equals(name, promotion.name)
                && Objects.equals(startAt, promotion.startAt)
                && Objects.equals(endAt, promotion.endAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, requiredCondition, giftQuantity, startAt, endAt);
    }
}
