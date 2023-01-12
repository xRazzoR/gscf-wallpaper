package com.gscf.wallpapercalculator.service;

import com.gscf.wallpapercalculator.model.CalculationResponse;
import com.gscf.wallpapercalculator.model.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WallpaperCalculatorServiceTest {

    private final WallpaperCalculatorService underTest;
    public WallpaperCalculatorServiceTest() {
        underTest = new WallpaperCalculatorService();
    }

    @Test
    void calculateShouldReturnNoError() {
        //GIVEN
        final List<Room> rooms = mockRooms();
        //WHEN
        final CalculationResponse response = underTest.calculate(rooms);
        //THEN
        assertNotNull(response);
        assertEquals(rooms.size(), response.getTotalNumberOfRooms());
        assertNull(response.getValidationErrors());
    }

    @Test
    void calculateShouldReturnNoErrorWithCubicRoom() {
        //GIVEN
        final List<Room> rooms = mockRooms();
        rooms.add(Room.builder().length(5).height(5).width(5).build());
        //WHEN
        final CalculationResponse response = underTest.calculate(rooms);
        //THEN
        assertNotNull(response);
        assertEquals(rooms.size(), response.getTotalNumberOfRooms());
        assertNull(response.getValidationErrors());
        assertEquals(1, response.getCubicShapeRooms().size());
    }

    @Test
    void calculateShouldReturnNoErrorWithDuplicateRoom() {
        //GIVEN
        final List<Room> rooms = mockRooms();
        rooms.add(Room.builder().length(77).height(99).width(66).build());
        rooms.add(Room.builder().length(77).height(99).width(66).build());
        //WHEN
        final CalculationResponse response = underTest.calculate(rooms);
        //THEN
        assertNotNull(response);
        assertEquals(rooms.size(), response.getTotalNumberOfRooms());
        assertNull(response.getValidationErrors());
        assertEquals(1, response.getDuplicateRooms().size());
    }

    private List<Room> mockRooms(){
        final List<Room> mockedRooms = new ArrayList<>();

        mockedRooms.add(Room.builder().length(1).height(1).width(50).build());
        mockedRooms.add(Room.builder().length(2).height(3).width(4).build());
        mockedRooms.add(Room.builder().length(5).height(6).width(7).build());
        mockedRooms.add(Room.builder().length(8).height(9).width(10).build());

        return mockedRooms;
    }
}