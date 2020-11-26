package io.javabrains.nnpda.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorViewModel {
    private int id;
    private String name;
    private float measurement;
}
