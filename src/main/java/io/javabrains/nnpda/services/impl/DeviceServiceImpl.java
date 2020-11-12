package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.repository.DeviceRepository;
import io.javabrains.nnpda.services.DeviceService;
import io.javabrains.nnpda.services.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final SecurityContextService securityContextService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, SecurityContextService securityContextService) {
        this.deviceRepository = deviceRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public List<Device> findAllUserDevices() {
        User user = securityContextService.GetAuthenticatedUser();
        List<Device> devices = new ArrayList<>();

        if (user != null) {
            //deviceRepository.findAllByUser_Id(user.getId()).iterator().forEachRemaining((book -> devices.add(new Device(book.getId(), book.getName(), null))));
            deviceRepository.findAllByUser_Id(user.getId()).iterator().forEachRemaining((devices::add));
        }

        return devices;
    }
}
