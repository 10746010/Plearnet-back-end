package tw.edu.ntub.imd.plearnet.config.parammapping;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.Objects;

public class ParamNameDataBinder extends ExtendedServletRequestDataBinder {
    private final Map<String, String> renameMapping;

    public ParamNameDataBinder(Object target, String objectName, Map<String, String> renameMapping) {
        super(target, objectName);
        this.renameMapping = renameMapping;
    }

    @Override
    protected void addBindValues(@NonNull MutablePropertyValues mpvs, @NonNull ServletRequest request) {
        super.addBindValues(mpvs, request);
        for (Map.Entry<String, String> entry : renameMapping.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (mpvs.contains(from)) {
                mpvs.add(to, Objects.requireNonNull(mpvs.getPropertyValue(from)).getValue());
            }
        }
    }
}
