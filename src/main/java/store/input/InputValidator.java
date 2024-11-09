package store.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final String ITEM_PATTERN = "\\[([^-]+)-([0-9]+)\\](,\\[([^-]+)-([0-9]+)\\])*";
    private static final Pattern pattern = Pattern.compile(ITEM_PATTERN);

    public static void validateInputFormat(String input) {
        if (!isValidFormat(input)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다.");
        }
    }

    public static boolean isValidFormat(String input) {
        String trimmedInput = input.replaceAll("\\s+", "");
        Matcher matcher = pattern.matcher(trimmedInput);
        return matcher.matches();
    }

}
