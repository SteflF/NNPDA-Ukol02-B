package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.dto.SensorInputModel;
import io.javabrains.nnpda.model.dto.SensorViewModel;
import io.javabrains.nnpda.services.SensorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensors")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<List<Sensor>> GetSensors() {
        return new ApiResponse<>(200, "SUCCESS", sensorService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Sensor> GetSensor(@PathVariable int id) {
        return new ApiResponse<>(200, "SUCCESS", sensorService.findById(id));
    }

    @PostMapping("/create")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<SensorViewModel> CreateSensor(@RequestBody SensorInputModel sensor) {
        if (sensorService.sensorAlreadyExists(sensor)) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS", null);
        }

        SensorViewModel result = sensorService.createSensor(sensor);
        if (result != null) {
            return new ApiResponse<>(200, "SUCCESS", result);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-CREATE", null);
        }
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Sensor> EditSensor(@PathVariable int id, @RequestBody SensorInputModel sensor) {
        if (sensorService.sensorAlreadyExists(sensor)) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS", null);
        }

        Sensor result = sensorService.editSensor(id, sensor);
        if (result != null) {
            return new ApiResponse<>(200, "SUCCESS", result);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-EDIT", null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ApiResponse<Sensor> DeleteSensor(@PathVariable int id) {
        if (sensorService.findById(id) == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-FIND", null);
        }

        if (sensorService.deleteSensor(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "CANNOT-DELETE", null);
        }
    }

    @GetMapping("/deviceSensors/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<List<SensorViewModel>> GetDeviceSensors(@PathVariable int id) {
        return new ApiResponse<>(200, "SUCCESS", sensorService.findByDeviceId(id));
    }
}
