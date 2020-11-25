package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.repository.MeasurementRepository;
import io.javabrains.nnpda.repository.SensorRepository;
import io.javabrains.nnpda.services.MeasurementService;
import io.javabrains.nnpda.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("measurementService")
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SecurityService securityService;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository, SecurityService securityService, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.securityService = securityService;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Measurement getLatest(int id) {
        return null;
    }


}
