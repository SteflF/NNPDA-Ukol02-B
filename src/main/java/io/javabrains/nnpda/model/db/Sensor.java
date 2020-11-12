package io.javabrains.nnpda.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sensor")
public class Sensor {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false, length = 1000)
    private String name;

    @Getter
    @Setter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "device_id")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private Device device;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensor")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private final List<Measurement> measurement = new ArrayList<>();
}
