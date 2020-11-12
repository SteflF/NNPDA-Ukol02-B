package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.db.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    List<Device> findAllByUser_Id(int user_id);

    Optional<Device> findById(int id);

    Boolean existsByName(String name);

    //Optional<Device> findByIdAndUserUsername(Integer id, String user_username);

    //Optional<Device> findByName(String name);

    //List<Device> findAllByUserUsername(String user_username);
}
