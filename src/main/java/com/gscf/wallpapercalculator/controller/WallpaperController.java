package com.gscf.wallpapercalculator.controller;

import com.gscf.wallpapercalculator.model.CalculationResponse;
import com.gscf.wallpapercalculator.model.Room;
import com.gscf.wallpapercalculator.model.ValidationError;
import com.gscf.wallpapercalculator.service.RoomConverterService;
import com.gscf.wallpapercalculator.service.ValidatorService;
import com.gscf.wallpapercalculator.service.WallpaperCalculatorService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallpaper")
public class WallpaperController {

    private final RoomConverterService roomConverterService;
    private final ValidatorService validatorService;
    private final WallpaperCalculatorService wallpaperCalculatorService;

    public WallpaperController(RoomConverterService roomConverterService, ValidatorService validatorService, WallpaperCalculatorService wallpaperCalculatorService) {
        this.roomConverterService = roomConverterService;
        this.validatorService = validatorService;
        this.wallpaperCalculatorService = wallpaperCalculatorService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculateWallpaper(@RequestParam(name = "file", required = false) MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(CalculationResponse.builder().processingMessage("Please provide valid file.").build());
        }

        final List<String> lines = readFileToLines(file);

        final List<ValidationError> validationErrors = new ArrayList<>();
        final List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            final Optional<ValidationError> validationError = validatorService.validateWithLineNumber(line, 1+i);
            validationError.ifPresentOrElse(validationErrors::add, () -> rooms.add(roomConverterService.convert(line)));
        }
        final CalculationResponse calculationResponse = wallpaperCalculatorService.calculate(rooms);
        if (!validationErrors.isEmpty()) {
            calculationResponse.setValidationErrors(validationErrors);
            calculationResponse.setProcessingMessage("Processed with errors");
        }
        return ResponseEntity.ok(calculationResponse);
    }

    private List<String> readFileToLines(final MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return IOUtils.readLines(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
