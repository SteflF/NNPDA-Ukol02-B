package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.db.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {
    //Optional<Sensor> findByIdAndUserId(Integer id, Integer userId);
    //Optional<Sensor> findByIdAndUser_Id(Integer id, Integer userId);
    Optional<Sensor> findByIdAndUser_Id(int id, int user_id);

    boolean existsByName(String name);

    //List<Sensor> findAllByDeviceIdAndUserId(Integer deviceId, Integer userId);
    //List<Sensor> findAllByDevice_Id(int device_id, int user_id);
    List<Sensor> findAllByDevice_IdAndUser_Id(int device_id, int user_id);
}
