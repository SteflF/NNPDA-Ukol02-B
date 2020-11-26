package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.services.MeasurementService;
import io.javabrains.nnpda.services.SecurityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SecurityService securityService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SecurityService securityService) {
        this.measurementService = measurementService;
        this.securityService = securityService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Measurement> GetMeasurement(@PathVariable int id) {
        return new ApiResponse<>(200, "SUCCESS", measurementService.getLatest(id));
    }
}
