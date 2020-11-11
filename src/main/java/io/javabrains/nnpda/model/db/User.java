package io.javabrains.nnpda.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false, length = 35, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(length = 100)
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private List<Device> devices = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private final List<Sensor> sensors = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private final List<Measurement> measurement = new ArrayList<>();

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recover_token_id", referencedColumnName = "id", nullable = true)
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private RecoverToken recoverToken;
}
