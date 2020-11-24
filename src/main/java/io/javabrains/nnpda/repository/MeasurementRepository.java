package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.db.Measurement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {
    List<Measurement> findAllBySensorIdAndUser_Id(Integer sensorId, Integer userId);

    //Optional<Data> findByIdAndUserId(Integer id, Integer userId);
    //Optional<Data> findByIdAndUserId(Integer id, Integer user_id);
    Optional<Measurement> findByIdAndUser_Id(Integer id, Integer user_id);

}
