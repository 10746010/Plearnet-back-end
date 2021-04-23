package tw.edu.ntub.imd.plearnet.dto.file.io;

import java.util.Arrays;

public interface WritableFile {
    default void write(String... lines) {
        write(Arrays.asList(lines));
    }

    void write(Iterable<String> stringIterable);

    default void append(String... lines) {
        append(Arrays.asList(lines));
    }

    void append(Iterable<String> stringIterable);
}
