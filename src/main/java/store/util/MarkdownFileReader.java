package store.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkdownFileReader {
    protected static final String REGEX = ",";
    private static final MarkdownFileReader instance = new MarkdownFileReader();

    private MarkdownFileReader() {
    }

    public static MarkdownFileReader getInstance() {
        return instance;
    }

    public List<List<String>> readMarkdownFile(String filePath) {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            boolean isHeaderSkipped = false;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!isHeaderSkipped) {
                    isHeaderSkipped = true;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                data.add(parseCsvLine(line));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 파일 경로를 입력해주세요.");
        }
        return data;
    }

    protected List<String> parseCsvLine(String line) {
        return parseLine(line, REGEX);
    }

    protected List<String> parseLine(String line, String regex) {
        return new ArrayList<>(Arrays.asList(line.split(regex)));
    }

}
