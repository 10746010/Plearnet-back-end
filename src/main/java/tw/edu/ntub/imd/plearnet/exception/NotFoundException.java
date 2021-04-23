package tw.edu.ntub.imd.plearnet.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotFoundException extends ProjectException {
    public NotFoundException() {
        super("查無資料");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return "NotFound";
    }
}
