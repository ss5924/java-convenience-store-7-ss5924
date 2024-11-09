package store.common;

import store.util.MarkdownFileWriter;

import java.util.List;

public abstract class AbstractFileWriteService<T> {
    private final MarkdownFileWriter markdownFileWriter = MarkdownFileWriter.getInstance();

    protected abstract List<String> mapToStrings(T object);

    protected void writeAllObjects(String filePath, List<T> objects) {
        List<List<String>> data = convertObjectsToStrings(objects);
        writeFileData(filePath, data);
    }

    private List<List<String>> convertObjectsToStrings(List<T> objects) {
        return objects.stream()
                .map(this::mapToStrings)
                .toList();
    }

    private void writeFileData(String filePath, List<List<String>> data) {
        markdownFileWriter.writeMarkdownFile(filePath, data);
    }
}
