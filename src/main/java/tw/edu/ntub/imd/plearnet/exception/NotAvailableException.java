package tw.edu.ntub.imd.plearnet.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NotAvailableException extends ProjectException {

    public NotAvailableException() {
        super("資料不存在");
    }

    @Override
    public String getErrorCode() {
        return "User - no available";
    }
}
