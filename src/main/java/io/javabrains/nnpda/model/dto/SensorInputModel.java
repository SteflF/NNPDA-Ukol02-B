package io.javabrains.nnpda.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorInputModel {
    private int deviceId;
    private String name;
}
