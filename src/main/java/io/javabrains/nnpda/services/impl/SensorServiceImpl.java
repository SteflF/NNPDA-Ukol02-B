package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.repository.SensorRepository;
import io.javabrains.nnpda.services.SecurityContextService;
import io.javabrains.nnpda.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    private final SecurityContextService securityContextService;

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository, SecurityContextService securityContextService) {
        this.sensorRepository = sensorRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public List<Sensor> findAll() {
        List<Sensor> sensors = new ArrayList<>();

        sensorRepository.findAll().iterator().forEachRemaining(sensors::add);

        return sensors;
    }

    @Override
    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sensor> findByDeviceId(int deviceId) {
        User user = securityContextService.GetAuthenticatedUser();
        List<Sensor> sensors = new ArrayList<>();

        if(user != null){
            sensorRepository.findAllByDevice_IdAndUser_Id(deviceId, user.getId()).iterator().forEachRemaining(sensors::add);

            return sensors;
        }

        //bookAuthorRepository.findByBookId(bookId).iterator().forEachRemaining(bookAuthors::add);
        //bookAuthorRepository.findByBookId(bookId).iterator().forEachRemaining((bookAuthor -> sensors.add(bookAuthor.getAuthor())));
/*
        for (var bookAuthor:bookAuthors
        ) {
            authors.add(bookAuthor.getAuthor());
        }
*/
        return sensors;
    }
}
