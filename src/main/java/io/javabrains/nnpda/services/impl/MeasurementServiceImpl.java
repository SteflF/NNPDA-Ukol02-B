package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.Measurement;
import io.javabrains.nnpda.model.db.Role;
import io.javabrains.nnpda.model.db.Sensor;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.repository.MeasurementRepository;
import io.javabrains.nnpda.repository.RoleRepository;
import io.javabrains.nnpda.repository.SensorRepository;
import io.javabrains.nnpda.repository.UserRepository;
import io.javabrains.nnpda.services.MeasurementService;
import io.javabrains.nnpda.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("measurementService")
public class MeasurementServiceImpl implements MeasurementService {

    private final BCryptPasswordEncoder bcryptEncoder;
    private final MeasurementRepository measurementRepository;
    private final RoleRepository roleRepository;
    private final SecurityService securityService;
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    @Autowired
    public MeasurementServiceImpl(BCryptPasswordEncoder bcryptEncoder, MeasurementRepository measurementRepository, RoleRepository roleRepository, SecurityService securityService, SensorRepository sensorRepository, UserRepository userRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.measurementRepository = measurementRepository;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
        this.sensorRepository = sensorRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    private void generateValue(){
        List<Sensor> sensors = (List<Sensor>) sensorRepository.findAll();

        for (Sensor sensor:sensors) {
            Measurement measurement = new Measurement();
            User user = userRepository.findById(1).orElse(null);

            if (user == null){
                Role role = new Role();
                role.setName("ADMIN");
                roleRepository.save(role);

                user.setUsername("devmail");
                user.setPassword(bcryptEncoder.encode("heslo"));
                user.setRole(role);

                userRepository.save(user);
            }

            //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            //String formatDateTime = date.format(format);
            Random rand = new Random();
            measurement.setDate(LocalDateTime.now());
            measurement.setValue(0 + rand.nextFloat() * (50 - 0));
            measurement.setSensor(sensor);
            measurement.setUser(user);

            measurementRepository.save(measurement);

            System.out.println("Generuji hodnotu: " + measurement);
        }
    }

    @Override
    public Measurement getLatest(int id) {
        return measurementRepository.findFirstBySensor_IdOrderByDateDesc(id).orElse(null);
    }
}
