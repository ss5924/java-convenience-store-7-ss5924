package store.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MarkdownFileWriter {
    protected static final String REGEX = ",";
    private static final String COLUMN_HEADER = "name,price,quantity,promotion";
    private static final MarkdownFileWriter instance = new MarkdownFileWriter();

    private MarkdownFileWriter() {
    }

    public static MarkdownFileWriter getInstance() {
        return instance;
    }

    public void writeMarkdownFile(String filePath, List<List<String>> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(COLUMN_HEADER);
            writer.newLine();
            for (List<String> lineData : data) {
                String line = String.join(REGEX, lineData);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 쓰는 도중 문제가 발생했습니다: " + e.getMessage());
        }
    }

}
