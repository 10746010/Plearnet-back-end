package tw.edu.ntub.imd.plearnet.exception.file;

import tw.edu.ntub.birc.common.exception.ProjectException;

public abstract class FileException extends ProjectException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return "File - " + getReason();
    }

    public abstract String getReason();
}
