package io.javabrains.nnpda.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "measurement")
public class Measurement {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false, length = 35)
    private LocalDateTime date;

    @Getter
    @Setter
    @Column(nullable = false)
    private Float value;

    @Getter
    @Setter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Getter
    @Setter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
