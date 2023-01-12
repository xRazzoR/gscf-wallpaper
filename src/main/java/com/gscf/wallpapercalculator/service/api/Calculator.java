package com.gscf.wallpapercalculator.service.api;

import com.gscf.wallpapercalculator.model.CalculationResponse;
import com.gscf.wallpapercalculator.model.Room;

import java.util.List;

public interface Calculator {
    CalculationResponse calculate(final List<Room> rooms);
}
