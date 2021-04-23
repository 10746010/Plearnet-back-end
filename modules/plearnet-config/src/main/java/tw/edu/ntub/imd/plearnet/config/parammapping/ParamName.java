package tw.edu.ntub.imd.plearnet.config.parammapping;

import java.lang.annotation.*;

/**
 * 用來指定參數別名，form-data、query parameter專用
 * <p>
 * RequestBody請使用{@link com.fasterxml.jackson.annotation.JsonAlias}
 *
 * @since 1.5.2
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {
    String[] value() default {};
}
