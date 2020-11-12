package io.javabrains.nnpda.model.db;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recover_token")
public class RecoverToken {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(nullable = false, length = 50)
    private String token;

    @Getter
    @Setter
    @OneToOne(mappedBy = "recoverToken")
    private User user;
}
