package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.SensorInputModel;
import io.javabrains.nnpda.model.dto.SensorViewModel;
import io.javabrains.nnpda.repository.DeviceRepository;
import io.javabrains.nnpda.repository.MeasurementRepository;
import io.javabrains.nnpda.repository.SensorRepository;
import io.javabrains.nnpda.services.SecurityService;
import io.javabrains.nnpda.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final SecurityService securityService;

    @Autowired
    public SensorServiceImpl(DeviceRepository deviceRepository, MeasurementRepository measurementRepository, SensorRepository sensorRepository, SecurityService securityService) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.securityService = securityService;
    }

    @Override
    public List<Sensor> findAll() {
        List<Sensor> sensors = new ArrayList<>();

        sensorRepository.findAll().iterator().forEachRemaining(sensors::add);

        return sensors;
    }

    @Override
    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElse(null);
    }

    @Override
    public SensorViewModel createSensor(SensorInputModel sensor) {
        User user = securityService.GetAuthenticatedUser();
        Device device = deviceRepository.findById(sensor.getDeviceId()).orElse(null);

        if (device != null && user != null) {
            Sensor newSensor = new Sensor();

            newSensor.setName(sensor.getName());
            newSensor.setUser(user);
            newSensor.setDevice(device);
            sensorRepository.save(newSensor);

            return new SensorViewModel(newSensor.getId(), newSensor.getName(), 0);
        }

        return null;
    }

    @Override
    public Sensor editSensor(int id, SensorInputModel sensorIM) {
        User user = securityService.GetAuthenticatedUser();
        Sensor sensor = sensorRepository.findByIdAndDevice_Id(id, sensorIM.getDeviceId()).orElse(null);

        if (user != null && sensor != null) {

            sensor.setName(sensorIM.getName());

            return sensorRepository.save(sensor);
        }

        return null;
    }

    @Override
    public Boolean deleteSensor(int id) {
        User user = securityService.GetAuthenticatedUser();
        Sensor sensor = sensorRepository.findById(id).orElse(null);

        if (user != null && sensor != null) {
            List<Measurement> measurements = measurementRepository.findAllBySensorIdAndUser_Id(id, user.getId());

            for (Measurement measurement:measurements) {
                measurementRepository.delete(measurement);
            }

            sensorRepository.delete(sensor);

            return true;
        }

        return false;
    }

    @Override
    public List<SensorViewModel> findByDeviceId(int deviceId) {
        User user = securityService.GetAuthenticatedUser();

        if(user != null){
            List<Sensor> sensors = sensorRepository.findAllByDevice_IdAndUser_Id(deviceId, user.getId());
            List<SensorViewModel> sensorsVM = new ArrayList<>();

            for (Sensor sensor:sensors) {
                Measurement measurement = measurementRepository.findFirstBySensor_IdOrderByDateDesc(sensor.getId()).orElse(null);
                sensorsVM.add(new SensorViewModel(sensor.getId(), sensor.getName(), measurement != null ? measurement.getValue() : 0));
            }

            //sensorRepository.findAllByDevice_IdAndUser_Id(deviceId, user.getId()).iterator().forEachRemaining(sensors::add);

            return sensorsVM;
        }

        return null;
    }

    @Override
    public Boolean sensorAlreadyExists(SensorInputModel sensor) {
        return sensorRepository.existsByName(sensor.getName());
    }
}
