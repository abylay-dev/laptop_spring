package kz.spring.laptop_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(length = 100)
    private String login;
    private String password;
    private String fio;

    public User(Integer id, String login, String password, String fio) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fio = fio;
    }
}
