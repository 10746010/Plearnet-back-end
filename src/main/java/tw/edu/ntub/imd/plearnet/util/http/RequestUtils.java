package tw.edu.ntub.imd.plearnet.util.http;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class RequestUtils {
    public static final String HEADER_NAME_LANGUAGE = "Language";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String HEADER_METHOD_OVERRIDE = "X-HTTP-Method-Override";
    public static final String HEADER_METHOD_OVERRIDE_TO_GET = HEADER_METHOD_OVERRIDE + "=GET";
    public static final String HEADER_METHOD_OVERRIDE_TO_POST = HEADER_METHOD_OVERRIDE + "=POST";
    public static final String HEADER_METHOD_OVERRIDE_TO_PUT = HEADER_METHOD_OVERRIDE + "=PUT";
    public static final String HEADER_METHOD_OVERRIDE_TO_PATCH = HEADER_METHOD_OVERRIDE + "=PATCH";
    public static final String HEADER_METHOD_OVERRIDE_TO_DELETE = HEADER_METHOD_OVERRIDE + "=DELETE";

    private RequestUtils() {

    }

    // 判斷該請求是否為ajax或app request
    public static boolean isAjax(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && (ajaxFlag.equals("XMLHttpRequest") || ajaxFlag.equalsIgnoreCase("AppRequest"));
    }

    public static Locale getRequestLocale() {
        return RequestContextUtils.getLocale(getRequest());
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }
}
