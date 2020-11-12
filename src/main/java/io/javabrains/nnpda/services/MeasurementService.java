package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.MeasurementInputModel;

public interface MeasurementService {
    Measurement save(MeasurementInputModel measurement, Sensor sensor, User user);

    boolean delete(int id);
}
