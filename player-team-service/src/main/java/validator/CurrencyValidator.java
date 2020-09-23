package validator;

import org.springframework.stereotype.Component;

import javax.money.Monetary;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public void initialize(ValidCurrency contactNumber) {
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        return Monetary.isCurrencyAvailable(currency);
    }
}
