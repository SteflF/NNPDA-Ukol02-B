package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.MeasurementInputModel;
import io.javabrains.nnpda.repository.MeasurementRepository;
import io.javabrains.nnpda.services.MeasurementService;
import io.javabrains.nnpda.services.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dataService")
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final SecurityContextService securityContextService;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository, SecurityContextService securityContextService) {
        this.measurementRepository = measurementRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public Measurement save(MeasurementInputModel data, Sensor sensor, User user) {
        Measurement newMeasurement = new Measurement();

        newMeasurement.setSensor(sensor);
        newMeasurement.setUser(user);
        newMeasurement.setDate(data.getDate());
        newMeasurement.setValue(data.getValue());

        return measurementRepository.save(newMeasurement);
    }

    @Override
    public boolean delete(int id) {
        User user = securityContextService.GetAuthenticatedUser();
        Measurement measurement = measurementRepository.findByIdAndUser_Id(id, user.getId()).orElse(null);

        if(measurement != null){
            measurementRepository.delete(measurement);

            return true;
        }

        return false;
    }
}