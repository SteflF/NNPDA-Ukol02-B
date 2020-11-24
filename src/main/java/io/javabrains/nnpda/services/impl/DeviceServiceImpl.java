package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.DeviceInputModel;
import io.javabrains.nnpda.repository.DeviceRepository;
import io.javabrains.nnpda.repository.MeasurementRepository;
import io.javabrains.nnpda.repository.SensorRepository;
import io.javabrains.nnpda.services.DeviceService;
import io.javabrains.nnpda.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final SecurityService securityService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, MeasurementRepository measurementRepository, SensorRepository sensorRepository, SecurityService securityService) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.securityService = securityService;
    }

    @Override
    public List<Device> findAllUserDevices() {
        User user = securityService.GetAuthenticatedUser();
        List<Device> devices = new ArrayList<>();

        if (user != null) {
            //deviceRepository.findAllByUser_Id(user.getId()).iterator().forEachRemaining((book -> devices.add(new Device(book.getId(), book.getName(), null))));
            deviceRepository.findAllByUser_Id(user.getId()).iterator().forEachRemaining((devices::add));
        }

        return devices;
    }

    @Override
    public Device createDevice(DeviceInputModel device) {
        User user = securityService.GetAuthenticatedUser();

        if (user != null){
            Device newDevice = new Device();

            newDevice.setName(device.getName());
            newDevice.setUser(securityService.GetAuthenticatedUser());

            return deviceRepository.save(newDevice);
        }

        return null;
    }

    @Override
    public Device editDevice(int id, DeviceInputModel deviceInputModel) {
        Device deviceWithSameName = deviceRepository.findByIdAndName(id, deviceInputModel.getName()).orElse(null);

        if (deviceWithSameName == null && !deviceRepository.existsByName(deviceInputModel.getName())) {
            Device device = findById(id);
            if (device != null) {
                device.setName(deviceInputModel.getName());

                deviceRepository.save(device);
                return device;
            }
        }

        return null;
    }

    @Override
    public Boolean deleteDevice(int id) {
        User user = securityService.GetAuthenticatedUser();

        if (user != null) {
            Device device = deviceRepository.findByIdAndUser_Id(id, user.getId()).orElse(null);
            List<Sensor> sensors = sensorRepository.findAllByDevice_IdAndUser_Id(id, user.getId());

            if (sensors != null){
                for (Sensor sensor: sensors) {
                    List<Measurement> measurements = measurementRepository.findAllBySensorIdAndUser_Id(sensor.getId(), user.getId());

                    for (Measurement measurement: measurements) {
                        measurementRepository.delete(measurement);
                    }

                    sensorRepository.delete(sensor);
                }
            }

            deviceRepository.delete(device);
            return true;
        }

        return false;
    }

    @Override
    public Device findById(int id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deviceAlreadyExists(DeviceInputModel device) {
        return deviceRepository.existsByName(device.getName());
    }
}
