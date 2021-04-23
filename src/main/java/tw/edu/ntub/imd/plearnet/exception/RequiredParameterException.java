package tw.edu.ntub.imd.plearnet.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class RequiredParameterException extends ProjectException {
    private final String requiredParameterName;

    public RequiredParameterException(String requiredParameterName) {
        super("缺少必要參數：" + requiredParameterName);
        this.requiredParameterName = requiredParameterName;
    }

    @Override
    public String getErrorCode() {
        return "Required - " + requiredParameterName;
    }
}
