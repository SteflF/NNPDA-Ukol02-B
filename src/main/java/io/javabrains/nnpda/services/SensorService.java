package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> findAll();

    Sensor findById(int id);

    List<Sensor> findByDeviceId(int id);
}
