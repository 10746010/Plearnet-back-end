package tw.edu.ntub.imd.plearnet.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class MethodNotSupportedException extends ProjectException {
    public MethodNotSupportedException(String url, String methodName, Throwable cause) {
        super(url + " 不支援此方法：" + methodName.toUpperCase(), cause);
    }

    @Override
    public String getErrorCode() {
        return "Http - MethodNotAllowed";
    }
}
