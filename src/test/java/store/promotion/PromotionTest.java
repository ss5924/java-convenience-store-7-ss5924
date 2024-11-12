package store.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.promotion.Promotion;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PromotionTest {
    private LocalDateTime baseDate;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        baseDate = LocalDateTime.of(2024, 11, 5, 23, 59, 59);
        now = DateTimes.now();
    }

    @Test
    void 프로모션_구매_참여조건은_0이하면_예외가_발생한다() {
        LocalDateTime startAt = baseDate;
        LocalDateTime endAt = startAt.plusDays(1);

        assertDoesNotThrow(() -> new Promotion("프로모션명", 1, 1, startAt, endAt));

        assertThatThrownBy(() -> new Promotion("프로모션명", 0, 1, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션_증정품개수는_0미만이면_예외가_발생한다() {
        LocalDateTime startAt = baseDate;
        LocalDateTime endAt = startAt.plusDays(1);

        assertDoesNotThrow(() -> new Promotion("프로모션명", 1, 0, startAt, endAt));

        assertThatThrownBy(() -> new Promotion("프로모션명", 1, -1, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션_종료일이_시작일보다_빠르면_예외가_발생한다() {
        LocalDateTime startAt = baseDate;
        LocalDateTime endAt = startAt.minusDays(1);

        assertThatThrownBy(() -> new Promotion("프로모션명", 1, -1, startAt, startAt))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Promotion("프로모션명", 1, -1, startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

}