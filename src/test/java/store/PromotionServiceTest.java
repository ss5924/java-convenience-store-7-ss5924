package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionServiceTest {
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        promotionService = new PromotionService();
    }

    @DisplayName("프로모션명으로 가져온 프로모션의 기간이 유효한지 확인 후 유효하지 않으면 예외가 발생한다.")
    @Test
    void getPromotionWithinValidPeriod() {
        assertThatThrownBy(() -> promotionService.getPromotionWithinValidPeriod("기간만료프로모션", DateTimes.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 프로모션명으로 프로모션을 가져오면 예외가 발생한다.")
    @Test
    void getPromotionNotExist() {
        assertThatThrownBy(() -> promotionService.getPromotion("없는이름"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("프로모션명으로 프로모션을 가져온다.")
    @Test
    void getPromotion() {
        Promotion promotion = promotionService.getPromotion("MD추천상품");
        assertEquals("MD추천상품", promotion.getName());
        assertEquals(1, promotion.getRequiredCondition());
        assertEquals(1, promotion.getGiftQuantity());
        assertEquals(parseLocalDateTime("2024-01-01"), promotion.getStartAt());
        assertEquals(parseLocalDateTime("2024-12-31"), promotion.getEndAt());
    }

    @DisplayName("모든 프로모션을 가져온다.")
    @Test
    void getAllPromotions() {
        assertEquals("탄산2+1", promotionService.getAllPromotions().get(0).getName());
        assertEquals("MD추천상품", promotionService.getAllPromotions().get(1).getName());
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