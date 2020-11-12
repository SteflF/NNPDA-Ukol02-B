package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.DeviceInputModel;
import io.javabrains.nnpda.repository.DeviceRepository;
import io.javabrains.nnpda.services.DeviceService;
import io.javabrains.nnpda.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final SecurityService securityService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, SecurityService securityService) {
        this.deviceRepository = deviceRepository;
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
    public Boolean deviceAlreadyExists(DeviceInputModel device) {
        return deviceRepository.existsByName(device.getName());
    }
}
