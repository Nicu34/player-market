package validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    @Override
    public void initialize(ValidDate contactNumber) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);

        return date != null && date.isBefore(currentDate);
    }
}
