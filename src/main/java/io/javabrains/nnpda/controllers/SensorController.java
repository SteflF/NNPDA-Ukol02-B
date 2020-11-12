package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.services.SensorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/deviceSensors/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<List<Sensor>> GetDeviceSensors(@PathVariable int id) {
        return new ApiResponse<>(200, "SUCCESS", sensorService.findByDeviceId(id));
    }
}
