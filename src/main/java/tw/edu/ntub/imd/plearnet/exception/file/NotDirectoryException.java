package tw.edu.ntub.imd.plearnet.exception.file;

import java.nio.file.Path;

public class NotDirectoryException extends tw.edu.ntub.imd.plearnet.exception.file.FileException {
    public NotDirectoryException(Path path) {
        super("\"" + path.toString() + "\"不是一個資料夾");
    }

    @Override
    public String getReason() {
        return "NotDirectory";
    }
}
