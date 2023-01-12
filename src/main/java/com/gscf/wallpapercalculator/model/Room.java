package com.gscf.wallpapercalculator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {
    private int length;
    private int width;
    private int height;
    private int sideA;
    private int sideB;
    private int sideC;
    private int totalWallpaperSize;
    private int extraWallpaperUsed;
}
