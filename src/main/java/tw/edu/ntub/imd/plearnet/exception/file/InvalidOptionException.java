package tw.edu.ntub.imd.plearnet.exception.file;

public class InvalidOptionException extends tw.edu.ntub.imd.plearnet.exception.file.FileException {

    public InvalidOptionException(Throwable cause) {
        super("開啟檔案方式(OpenOption)錯誤", cause);
    }

    @Override
    public String getReason() {
        return "InvalidOption";
    }
}
