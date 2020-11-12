package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.services.DeviceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<List<Device>> GetUserDevices() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", deviceService.findAllUserDevices());
    }
}
