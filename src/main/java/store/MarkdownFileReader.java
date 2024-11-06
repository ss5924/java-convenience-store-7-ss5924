package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkdownFileReader {
    private final static String REGEX = ",";

    public List<List<String>> readMarkdownFile(String filePath) {
        List<List<String>> data = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
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

    private List<String> parseCsvLine(String line) {
        return parseLine(line, REGEX);
    }

    private List<String> parseLine(String line, String regex) {
        return new ArrayList<>(Arrays.asList(line.split(regex)));
    }

}
