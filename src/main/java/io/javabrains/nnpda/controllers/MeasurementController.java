package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.MeasurementInputModel;
import io.javabrains.nnpda.services.MeasurementService;
import io.javabrains.nnpda.services.SecurityService;
import io.javabrains.nnpda.services.SensorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SecurityService securityService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SecurityService securityService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.securityService = securityService;
        this.sensorService = sensorService;
    }

    @PostMapping("/")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Measurement> saveMeasurement(@RequestBody MeasurementInputModel data){
        User user = securityService.GetAuthenticatedUser();
        Sensor sensor = sensorService.findById(data.getSensorId());

        if (sensor != null) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", measurementService.save(data, sensor, user));
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "INVALID", null);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Void> deleteMeasurement(@PathVariable int id){
        if (measurementService.delete(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "NOT-FOUND", null);
    }
}
