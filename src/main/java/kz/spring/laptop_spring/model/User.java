package kz.spring.laptop_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;

    @Column(unique = true)
    private String username;
    private String password;

    @Transient
    private String rePassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
