package com.gscf.wallpapercalculator.service;

import com.gscf.wallpapercalculator.model.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorServiceTest {

    private final ValidatorService underTest;

    public ValidatorServiceTest() {
        this.underTest = new ValidatorService();
    }

    @Test
    void validateShouldReturnNoError() {
        //GIVEN
        final String sampleInput = "10x50x20";
        //WHEN
        final Optional<ValidationError> response = underTest.validate(sampleInput);
        //THEN
        assertTrue(response.isEmpty());
    }

    @Test
    void validateShouldReturnInvalidInputError() {
        //GIVEN
        final String sampleInput = "c10x50x20";
        final ValidationError expected = ValidationError.builder()
                .validationMessages(Collections
                        .singletonList(String.format("Invalid input format: <%s>", sampleInput))).build();
        //WHEN
        final Optional<ValidationError> response = underTest.validate(sampleInput);
        //THEN
        assertFalse(response.isEmpty());
        assertEquals(expected, response.get());
    }
}