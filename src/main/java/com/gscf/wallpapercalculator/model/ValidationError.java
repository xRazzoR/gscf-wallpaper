package com.gscf.wallpapercalculator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ValidationError {
    private int lineNumber;
    private List<String> validationMessages;
}
