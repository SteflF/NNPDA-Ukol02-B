package io.javabrains.nnpda.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "device")
public class Device {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false, length = 500, unique = true)
    private String name;

    @Getter
    @Setter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER)
    private final List<Sensor> sensors = new ArrayList<>();
}
