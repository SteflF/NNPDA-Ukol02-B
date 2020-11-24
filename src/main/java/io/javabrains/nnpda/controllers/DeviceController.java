package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Device;
import io.javabrains.nnpda.model.dto.DeviceInputModel;
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

    @GetMapping("/devices/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Device> GetUserDevice(@PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", deviceService.findById(id));
    }

    @PostMapping("/create")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Device> CreateDevice(@RequestBody DeviceInputModel deviceInputModel) {
        if (deviceService.deviceAlreadyExists(deviceInputModel)) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS", null);
        }

        Device device = deviceService.createDevice(deviceInputModel);
        if (device != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", device);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-CREATE", null);
        }
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ApiResponse<Device> EditDevice(@PathVariable int id, @RequestBody DeviceInputModel deviceInputModel) {
        if (deviceService.findById(id) == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-FIND", null);
        }

        Device device = deviceService.editDevice(id, deviceInputModel);
        if (device != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", device);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-EDIT", null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ApiResponse<Device> DeleteDevice(@PathVariable int id) {
        if (deviceService.findById(id) == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-FIND", null);
        }

        if (deviceService.deleteDevice(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-DELETE", null);
        }
    }
}
