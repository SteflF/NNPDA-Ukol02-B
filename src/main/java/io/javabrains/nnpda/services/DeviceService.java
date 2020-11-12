package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.dto.DeviceInputModel;

import java.util.List;

public interface DeviceService {
    List<Device> findAllUserDevices();

    Device createDevice(DeviceInputModel device);

    Boolean deviceAlreadyExists(DeviceInputModel device);
}
