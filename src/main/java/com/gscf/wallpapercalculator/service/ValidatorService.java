package com.gscf.wallpapercalculator.service;

import com.gscf.wallpapercalculator.model.ValidationError;
import com.gscf.wallpapercalculator.service.api.Validator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorService implements Validator {
    @Override
    public Optional<ValidationError> validate(final String input) {
        final String regex = "^(\\d+x){2}\\d+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input.toLowerCase());
        if (!m.matches()) {
            return Optional.of(
                    ValidationError.builder()
                            .validationMessages(Collections.singletonList(String.format("Invalid input format: <%s>", input)))
                            .build());
        }
        return Optional.empty();
    }

    public Optional<ValidationError> validateWithLineNumber(final String input, final int lineNumber) {
        Optional<ValidationError> validationResult = validate(input);
        validationResult.ifPresent(validationError -> validationError.setLineNumber(lineNumber));
        return validationResult;
    }
}
