package tw.edu.ntub.imd.plearnet.exception.form;

import lombok.Getter;
import org.springframework.validation.FieldError;
import tw.edu.ntub.birc.common.exception.ProjectException;

@Getter
public class InvalidFormException extends ProjectException {
    public InvalidFormException(FieldError error) {
        this(error.getDefaultMessage());
    }

    public InvalidFormException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "FormValidation - Invalid";
    }
}
