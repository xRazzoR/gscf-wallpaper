package com.gscf.wallpapercalculator.service;

import com.gscf.wallpapercalculator.model.Room;
import com.gscf.wallpapercalculator.service.api.RoomConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class RoomConverterService implements RoomConverter {
    @Override
    public Room convert(final String roomSize) {
        final String[] roomDimensions = StringUtils.split(roomSize.toLowerCase(), 'x');

        return Room.builder()
                .length(Integer.parseInt(roomDimensions[0]))
                .width(Integer.parseInt(roomDimensions[1]))
                .height(Integer.parseInt(roomDimensions[2]))
                .build();
    }
}
