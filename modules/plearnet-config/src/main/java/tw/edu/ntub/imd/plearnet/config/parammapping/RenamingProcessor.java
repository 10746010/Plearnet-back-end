package tw.edu.ntub.imd.plearnet.config.parammapping;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import tw.edu.ntub.birc.common.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RenamingProcessor extends ServletModelAttributeMethodProcessor {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    //Rename cache
    private final Map<Class<?>, Map<String, String>> replaceMap = new ConcurrentHashMap<>();

    public RenamingProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, @NonNull NativeWebRequest nativeWebRequest) {
        Object target = binder.getTarget();
        Class<?> targetClass = Objects.requireNonNull(target).getClass();
        if (!replaceMap.containsKey(targetClass)) {
            Map<String, String> mapping = analyzeClass(targetClass);
            replaceMap.put(targetClass, mapping);
        }
        Map<String, String> mapping = replaceMap.get(targetClass);
        ParamNameDataBinder paramNameDataBinder = new ParamNameDataBinder(target, binder.getObjectName(), mapping);
        Objects.requireNonNull(requestMappingHandlerAdapter.getWebBindingInitializer()).initBinder(paramNameDataBinder);
        super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
    }

    private static Map<String, String> analyzeClass(Class<?> targetClass) {
        Set<Field> fields = FieldUtils.getAllFieldWithoutStatic(targetClass);
        Map<String, String> renameMap = new HashMap<>();
        for (Field field : fields) {
            ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
            JsonAlias jsonAlias = field.getAnnotation(JsonAlias.class);
            List<String> paramNameList = new ArrayList<>();
            if (paramNameAnnotation != null && paramNameAnnotation.value().length > 0) {
                Arrays.stream(paramNameAnnotation.value())
                        .filter(StringUtils::isNotBlank)
                        .forEach(paramNameList::add);
            }
            if (jsonAlias != null && jsonAlias.value().length > 0) {
                Arrays.stream(jsonAlias.value())
                        .filter(StringUtils::isNotBlank)
                        .forEach(paramNameList::add);
            }
            paramNameList.stream().distinct().forEach(paramName -> renameMap.put(paramName, field.getName()));
        }
        if (renameMap.isEmpty()) {
            return Collections.emptyMap();
        }
        return renameMap;
    }
}
