package com.gscf.wallpapercalculator.service.api;

import com.gscf.wallpapercalculator.model.ValidationError;

import java.util.Optional;

public interface Validator {
    Optional<ValidationError> validate(final String input);
}
