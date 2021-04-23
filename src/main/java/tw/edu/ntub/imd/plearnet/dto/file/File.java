package tw.edu.ntub.imd.plearnet.dto.file;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tw.edu.ntub.imd.plearnet.dto.file.directory.Directory;
import tw.edu.ntub.imd.plearnet.exception.file.FileUnknownException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public interface File extends Copyable {
    default String getFullFileName() {
        return getName() + "." + getExtension();
    }

    @Nullable
    String getName();

    void setName(String name);

    @NonNull
    String getExtension();

    @Nullable
    Path getPath();

    void setPath(Path path);

    default long getSize() {
        try {
            Path absolutePath = getPath();
            return absolutePath != null ? Files.size(absolutePath) : 0;
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    default void delete() {
        try {
            Path absolutePath = getPath();
            if (absolutePath != null) {
                Files.deleteIfExists(absolutePath);
            }
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    default boolean isNotExist() {
        return !isExist();
    }

    default boolean isExist() {
        Path absolutePath = getPath();
        return absolutePath != null && Files.exists(absolutePath);
    }

    default void create() {
        try {
            Path absolutePath = Objects.requireNonNull(getPath(), "該檔案沒有設定路徑");
            if (isNotExist()) {
                Files.createFile(absolutePath);
            }
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    default InputStream getInputStream(StandardOpenOption... options) {
        try {
            Path absolutePath = Objects.requireNonNull(getPath(), "該檔案沒有設定路徑");
            return Files.newInputStream(absolutePath, options);
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }

    @Override
    default void copyTo(Directory newDirectory, StandardCopyOption... options) {
        try {
            Path absolutePath = Objects.requireNonNull(getPath(), "該檔案沒有設定路徑");
            Path directoryPath = Objects.requireNonNull(newDirectory.getAbsolutePath(), "該資料夾沒有設定路徑");
            Files.copy(absolutePath, directoryPath, options);
        } catch (IOException e) {
            throw new FileUnknownException(e);
        }
    }
}
