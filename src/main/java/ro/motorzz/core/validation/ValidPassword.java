package ro.motorzz.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Luci on 23-Jun-17.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {

    String message() default "Invalid password";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}