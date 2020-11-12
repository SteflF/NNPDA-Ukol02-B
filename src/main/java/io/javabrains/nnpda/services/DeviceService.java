package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.Device;

import java.util.List;

public interface DeviceService {
    List<Device> findAllUserDevices();
}
