package tw.edu.ntub.imd.plearnet.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NoDataException extends ProjectException {

    public NoDataException() {
        super("查無資料");
    }

    @Override
    public String getErrorCode() {
        return "User - no data";
    }
}
