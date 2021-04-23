package tw.edu.ntub.imd.plearnet.dto.file;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import tw.edu.ntub.imd.plearnet.exception.file.FileExtensionNotFoundException;
import tw.edu.ntub.imd.plearnet.util.file.FileUtils;

import java.nio.file.Path;

public class CommonFile implements File {
    private String name;
    private final String extension;
    private Path path;

    public CommonFile(@NonNull String fullFileName) {
        this.name = FileUtils.getFileName(fullFileName);
        this.extension = FileUtils.getFileExtension(fullFileName);
        this.path = null;
    }

    public CommonFile(@NonNull Path path) {
        Path fileNamePath = path.getFileName();
        this.name = fileNamePath.toString();
        String tempExtension;
        try {
            tempExtension = FileUtils.getFileExtension(name);
        } catch (FileExtensionNotFoundException e) {
            tempExtension = "";
        }
        this.extension = tempExtension;
        this.path = path;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getExtension() {
        return extension;
    }

    @Nullable
    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }
}
