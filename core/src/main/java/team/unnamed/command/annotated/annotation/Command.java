package team.unnamed.command.annotated.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for indicating that the annotated
 * method or class must be converted to a Command.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Command {

    /**
     * Returns the name of the command
     */
    String name();

    /**
     * Returns the aliases of the command
     */
    String[] aliases() default {};

    /**
     * Returns the description of the command
     */
    String desc() default "";

    /**
     * Returns the permission node of the command
     */
    String permission() default "";

}
