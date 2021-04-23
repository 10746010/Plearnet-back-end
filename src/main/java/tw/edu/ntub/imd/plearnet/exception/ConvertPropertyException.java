package tw.edu.ntub.imd.plearnet.exception;

import org.springframework.beans.InvalidPropertyException;
import tw.edu.ntub.birc.common.exception.ProjectException;

public class ConvertPropertyException extends ProjectException {
    public ConvertPropertyException(InvalidPropertyException cause) {
        super(cause.getPropertyName() + "無法轉換成" + cause.getBeanClass().getSimpleName(), cause);
    }

    @Override
    public String getErrorCode() {
        return "RequestParameter - ConvertFail";
    }
}
