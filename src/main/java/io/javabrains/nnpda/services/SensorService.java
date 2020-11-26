package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.dto.SensorInputModel;
import io.javabrains.nnpda.model.dto.SensorViewModel;

import java.util.List;

public interface SensorService {
    List<Sensor> findAll();

    Sensor findById(int id);

    SensorViewModel createSensor(SensorInputModel sensor);

    Sensor editSensor(int id, SensorInputModel sensor);

    Boolean deleteSensor(int id);

    List<SensorViewModel> findByDeviceId(int id);

    Boolean sensorAlreadyExists(SensorInputModel sensor);
}
