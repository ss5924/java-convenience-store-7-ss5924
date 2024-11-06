package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownFileReaderTest {
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private MarkdownFileReader markdownFileReader;

    @BeforeEach
    void setUp() {
        markdownFileReader = new MarkdownFileReader();
    }

    @Test
    void 파일의_첫번째라인을_읽는다() {
        List<List<String>> readLines = markdownFileReader.readMarkdownFile(PRODUCT_FILE_PATH);
        assertEquals(List.of("콜라", "1000", "10", "탄산2+1"), readLines.get(0));
    }

    @Test
    void 파일의_두번째라인을_읽는다() {
        List<List<String>> readLines = markdownFileReader.readMarkdownFile(PRODUCT_FILE_PATH);
        assertEquals(List.of("콜라", "1000", "10", "null"), readLines.get(1));
    }

    @Test
    void 잘못된_파일경로가_입력되면_예외가_발생한다() {
        assertThatThrownBy(() -> markdownFileReader.readMarkdownFile("invalid/path"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}