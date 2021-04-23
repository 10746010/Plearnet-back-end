package tw.edu.ntub.imd.plearnet.util.http;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import tw.edu.ntub.imd.plearnet.exception.form.InvalidFormException;

@UtilityClass
public class BindingResultUtils {
    public void validate(BindingResult bindingResult) throws InvalidFormException {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            throw new InvalidFormException(bindingResult.getFieldError());
        }
    }
}
