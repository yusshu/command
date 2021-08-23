package team.unnamed.command.annotated.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for string parameters, indicates
 * that it will consume all the remaining arguments,
 * compatible with {@link Quoted}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Infinite {

    /**
     * Returns the delimiter where
     * the string arguments will join
     */
    String value() default " ";

}
