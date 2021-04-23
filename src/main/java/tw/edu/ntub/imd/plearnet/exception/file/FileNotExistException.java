package tw.edu.ntub.imd.plearnet.exception.file;

import java.nio.file.Path;

public class FileNotExistException extends FileException {
    public FileNotExistException(Path filePath) {
        this(filePath.toString());
    }

    public FileNotExistException(String filePath) {
        super("此檔案不存在: " + filePath);
    }

    @Override
    public String getReason() {
        return "NotExists";
    }
}
