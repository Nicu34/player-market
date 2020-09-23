package validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid date. Date cannot be greater than current date. Format should be yyyy-MM-dd. Example: 2020-12-20";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
