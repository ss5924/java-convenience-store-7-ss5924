package store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public abstract class AbstractFileReadService {
    protected final MarkdownFileReader markdownFileReader;

    public AbstractFileReadService(MarkdownFileReader markdownFileReader) {
        this.markdownFileReader = markdownFileReader;
    }

    protected List<List<String>> getFileLines(String filePath) {
        return markdownFileReader.readMarkdownFile(filePath);
    }

    protected int parseInt(String element) {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요. element=" + element);
        }
    }

    protected LocalDateTime parseLocalDateTime(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return localDate.atStartOfDay();
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 날짜 형식으로 입력해주세요. date=" + date);
        }
    }
}
