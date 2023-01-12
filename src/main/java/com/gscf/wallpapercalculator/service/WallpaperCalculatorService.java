package com.gscf.wallpapercalculator.service;

import com.gscf.wallpapercalculator.model.CalculationResponse;
import com.gscf.wallpapercalculator.model.Room;
import com.gscf.wallpapercalculator.service.api.Calculator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WallpaperCalculatorService implements Calculator {
    @Override
    public CalculationResponse calculate(final List<Room> rooms) {
        final Set<Room> duplicates = new HashSet<>();
        final Set<Room> uniques = new HashSet<>();
        final List<Room> cubicRooms = new ArrayList<>();
        int totalWallpaperNeeded = 0;

        for (final Room room : rooms) {
            calculateSides(room);
            calculateExtra(room);
            calculateTotalWallpaper(room);

            //Set returns true if element does not exist
            if (!uniques.add(room)) {
                duplicates.add(room);
            }
            if (isCubicShape(room)) {
                cubicRooms.add(room);
            }
            totalWallpaperNeeded += room.getTotalWallpaperSize();
        }

        cubicRooms.sort(Comparator.comparingInt(Room::getTotalWallpaperSize).reversed());

        return CalculationResponse
                .builder()
                .totalNumberOfRooms(rooms.size())
                .totalWallpaperNeeded(totalWallpaperNeeded)
                .cubicShapeRooms(cubicRooms)
                .duplicateRooms(duplicates)
                .build();
    }

    private void calculateSides(final Room room) {
        room.setSideA(room.getLength() * room.getWidth());
        room.setSideB(room.getWidth() * room.getHeight());
        room.setSideC(room.getHeight() * room.getLength());
    }

    private void calculateExtra(final Room room) {
        final List<Integer> sides = List.of(room.getSideA(), room.getSideB(), room.getSideC());
        room.setExtraWallpaperUsed(sides.get(sides.indexOf(Collections.min(sides))));
    }

    private void calculateTotalWallpaper(final Room room) {
        room.setTotalWallpaperSize((2 * (room.getSideA() + room.getSideB() + room.getSideC())) + room.getExtraWallpaperUsed());
    }

    private boolean isCubicShape(final Room room) {
        return (room.getLength() == room.getWidth()) && (room.getLength() == room.getHeight());
    }
}
