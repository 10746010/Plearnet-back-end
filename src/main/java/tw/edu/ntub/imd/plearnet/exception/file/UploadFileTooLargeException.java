package tw.edu.ntub.imd.plearnet.exception.file;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class UploadFileTooLargeException extends FileException {
    public UploadFileTooLargeException(MaxUploadSizeExceededException cause) {
        super("檔案大小超過" + cause.getMaxUploadSize() + "：" + ((FileSizeLimitExceededException) cause.getRootCause()).getFileName(), cause);
    }

    @Override
    public String getReason() {
        return "TooLarge";
    }
}
