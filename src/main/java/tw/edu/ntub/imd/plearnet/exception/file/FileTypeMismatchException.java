package tw.edu.ntub.imd.plearnet.exception.file;

import java.nio.file.Path;

public class FileTypeMismatchException extends FileException {
    public FileTypeMismatchException(Path path, Class<?> aClass) {
        super("此檔案類型不是" + aClass.getName() + ": " + path);
    }

    @Override
    public String getReason() {
        return "TypeMismatch";
    }
}
