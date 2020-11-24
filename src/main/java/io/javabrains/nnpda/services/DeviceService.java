package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.dto.DeviceInputModel;

import java.util.List;

public interface DeviceService {
    List<Device> findAllUserDevices();

    Device createDevice(DeviceInputModel device);

    Device editDevice(int id, DeviceInputModel device);

    Boolean deleteDevice(int id);

    Device findById(int id);

    Boolean deviceAlreadyExists(DeviceInputModel device);
}
