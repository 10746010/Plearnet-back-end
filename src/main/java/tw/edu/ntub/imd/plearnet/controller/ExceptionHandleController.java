package tw.edu.ntub.imd.plearnet.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.birc.common.exception.date.ParseDateException;
import tw.edu.ntub.birc.common.util.ClassUtils;
import tw.edu.ntub.imd.plearnet.exception.ConvertPropertyException;
import tw.edu.ntub.imd.plearnet.exception.MethodNotSupportedException;
import tw.edu.ntub.imd.plearnet.exception.NullRequestBodyException;
import tw.edu.ntub.imd.plearnet.exception.RequiredParameterException;
import tw.edu.ntub.imd.plearnet.exception.file.FileNotExistException;
import tw.edu.ntub.imd.plearnet.exception.file.UploadFileTooLargeException;
import tw.edu.ntub.imd.plearnet.exception.form.InvalidFormDateFormatException;
import tw.edu.ntub.imd.plearnet.exception.form.InvalidFormException;
import tw.edu.ntub.imd.plearnet.exception.form.InvalidFormNumberFormatException;
import tw.edu.ntub.imd.plearnet.exception.form.InvalidRequestFormatException;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Log4j2
@ControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException e) {
        return ResponseEntityBuilder.error(e).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormatException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
            List<JsonMappingException.Reference> referenceList = invalidFormatException.getPath();
            String message = "";
            if (referenceList.size() > 0) {
                JsonMappingException.Reference reference = referenceList.get(0);
                Object from = reference.getFrom();
                String fieldName = reference.getFieldName();
                Class<?> fromClass = from.getClass();
                Field declaredField = null;
                while (declaredField == null) {
                    try {
                        declaredField = fromClass.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException ignored) {
                        fromClass = fromClass.getSuperclass();
                    }
                }
                String description = declaredField.getName();
                if (ClassUtils.isCanCast(invalidFormatException.getTargetType(), Number.class)) {
                    message = description + " - \"" + invalidFormatException.getValue() + "\"輸入的文字中包含非數字文字";
                } else {
                    throw new UnknownException(e);
                }
            }
            return ResponseEntityBuilder.error(new InvalidRequestFormatException(message)).build();
        } else if (e.getRootCause() instanceof ParseDateException) {
            ParseDateException rootCause = (ParseDateException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormDateFormatException(rootCause)).build();
        } else if (e.getRootCause() instanceof NumberFormatException) {
            NumberFormatException rootCause = (NumberFormatException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormNumberFormatException(rootCause)).build();
        } else {
            return ResponseEntityBuilder.error(new NullRequestBodyException(e)).build();
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotExistException.class)
    public void handleFileNotExistException(FileNotExistException e) {
        log.error("找不到檔案", e);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntityBuilder.error(new UploadFileTooLargeException(e)).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request,
            HttpRequestMethodNotSupportedException e
    ) {
        return ResponseEntityBuilder.error(new MethodNotSupportedException(
                request.getRequestURL().toString(),
                request.getMethod(),
                e
        )).build();
    }

    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<String> handleInvalidPropertyException(InvalidPropertyException e) {
        return ResponseEntityBuilder.error(new ConvertPropertyException(e)).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        ConstraintViolation<?> constraintViolation = constraintViolations.stream().findAny().orElseThrow();
        return ResponseEntityBuilder.error(new InvalidFormException(constraintViolation.getMessage())).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntityBuilder.error(new RequiredParameterException(e.getParameterName())).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Exception e) {
        return ResponseEntityBuilder.error(new UnknownException(e)).build();
    }
}
