package com.gscf.wallpapercalculator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class CalculationResponse {
    private int totalNumberOfRooms;
    private int totalWallpaperNeeded;
    private List<Room> cubicShapeRooms;
    private Set<Room> duplicateRooms;
    private String processingMessage;
    private List<ValidationError> validationErrors;
}
