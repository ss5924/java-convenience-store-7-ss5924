package store.common;

import store.util.MarkdownFileReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractFileReadService<T> {
    private final MarkdownFileReader markdownFileReader = MarkdownFileReader.getInstance();

    protected abstract T mapToObject(List<String> line);

    protected List<T> getAllObjects(String filePath) {
        return convertStringsToObject(filePath, this::mapToObject);
    }

    private List<T> convertStringsToObject(String filePath, Function<List<String>, T> mapper) {
        List<List<String>> lines = readFileLines(filePath);
        List<T> items = new ArrayList<>();
        for (List<String> line : lines) {
            T item = mapper.apply(line);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    private List<List<String>> readFileLines(String filePath) {
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
