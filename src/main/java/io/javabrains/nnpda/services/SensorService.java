package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.dto.SensorInputModel;

import java.util.List;

public interface SensorService {
    List<Sensor> findAll();

    Sensor findById(int id);

    Sensor createSensor(SensorInputModel sensor);

    Sensor editSensor(int id, SensorInputModel sensor);

    Boolean deleteSensor(int id);

    List<Sensor> findByDeviceId(int id);

    Boolean sensorAlreadyExists(SensorInputModel sensor);
}
