package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @DisplayName("금액에 0원 미만을 입력하면 예외가 발생한다.")
    @Test
    void 금액에_0원_미만을_입력하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Product("콜라", -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}