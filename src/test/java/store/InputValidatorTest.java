package store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InputValidatorTest {

    @Test
    void 입력형식이_올바를때_통과하는지_확인한다() {
        assertDoesNotThrow(() -> InputValidator.validateInputFormat("[콜라-10],[사이다-1]"));
    }

    @Test
    void 입력형식이_올바를때_통과하는지_확인한다2() {
        assertDoesNotThrow(() -> InputValidator.validateInputFormat("[콜라-10]"));
    }

    @Test
    void 입력형식이_맞지않으면_예외가_발생한다() {
        assertThatThrownBy(() -> InputValidator.validateInputFormat("]콜라-10]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력형식이_맞지않으면_예외가_발생한다2() {
        assertThatThrownBy(() -> InputValidator.validateInputFormat("[콜라-10][사이다-1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력형식이_맞지않으면_예외가_발생한다3() {
        assertThatThrownBy(() -> InputValidator.validateInputFormat("콜라-10"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}