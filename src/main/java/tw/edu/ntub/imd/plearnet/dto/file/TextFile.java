package tw.edu.ntub.imd.plearnet.dto.file;

import org.springframework.lang.NonNull;
import tw.edu.ntub.imd.plearnet.dto.file.io.ReadableFile;
import tw.edu.ntub.imd.plearnet.dto.file.io.WritableFile;
import tw.edu.ntub.imd.plearnet.exception.file.FileUnknownException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public class TextFile extends CommonFile implements ReadableFile, WritableFile {

    public TextFile(@NonNull String fullFileName) {
        super(fullFileName);
    }

    public TextFile(Path path) {
        super(path);
    }

    @Override
    public Stream<String> readLinesWithStream() {
        Path absolutePath = getPath();
        if (absolutePath == null) {
            return Stream.empty();
        }
        Map<String, Charset> availableCharsetMap = Charset.availableCharsets();
        Collection<Charset> charsets = availableCharsetMap.values();
        Iterator<Charset> iterator = charsets.iterator();
        while (iterator.hasNext()) {
            try {
                return Files.lines(absolutePath, iterator.next());
            } catch (IOException e) {
                if (!iterator.hasNext()) {
                    throw new FileUnknownException(e);
                }
            }
        }
        return Stream.empty();
    }

    @Override
    public List<String> readLinesWithList() {
        Path absolutePath = getPath();
        if (absolutePath == null) {
            return Collections.emptyList();
        }
        Map<String, Charset> availableCharsetMap = Charset.availableCharsets();
        Collection<Charset> charsets = availableCharsetMap.values();
        Iterator<Charset> iterator = charsets.iterator();
        while (iterator.hasNext()) {
            try {
                return Files.readAllLines(absolutePath, iterator.next());
            } catch (IOException e) {
                if (!iterator.hasNext()) {
                    throw new FileUnknownException(e);
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void write(Iterable<String> stringIterable) {
        try {
            Path absolutePath = Objects.requireNonNull(getPath(), "該檔案沒有設定路徑");
            Files.write(absolutePath, stringIterable, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    @Override
    public void append(Iterable<String> stringIterable) {
        try {
            Path absolutePath = Objects.requireNonNull(getPath(), "該檔案沒有設定路徑");
            Files.write(absolutePath, stringIterable, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }
}
