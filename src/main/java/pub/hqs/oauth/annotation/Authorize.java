package pub.hqs.oauth.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface Authorize {
    String role() default "";
}
