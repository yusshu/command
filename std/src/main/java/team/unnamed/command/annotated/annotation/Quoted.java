package team.unnamed.command.annotated.annotation;

import team.unnamed.command.stack.ArgumentStack;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for string parameters, indicates
 * that the string will be get using
 * {@link ArgumentStack#nextQuoted()} instead
 * of {@link ArgumentStack#next()}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Quoted {
}
