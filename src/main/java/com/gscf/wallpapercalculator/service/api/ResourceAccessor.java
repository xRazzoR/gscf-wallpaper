package com.gscf.wallpapercalculator.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface ResourceAccessor {
    String persistFile(final MultipartFile file);
}
