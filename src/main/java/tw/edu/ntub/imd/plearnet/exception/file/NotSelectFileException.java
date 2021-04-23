package tw.edu.ntub.imd.plearnet.exception.file;

public class NotSelectFileException extends FileException {
    public NotSelectFileException(String parameterName) {
        super("沒有選擇檔案：" + parameterName);
    }

    @Override
    public String getReason() {
        return "NotChooseFile";
    }
}
