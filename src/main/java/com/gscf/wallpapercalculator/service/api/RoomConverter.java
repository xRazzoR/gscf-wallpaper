package com.gscf.wallpapercalculator.service.api;

import com.gscf.wallpapercalculator.model.Room;

public interface RoomConverter {
    Room convert(final String roomSize);
}
