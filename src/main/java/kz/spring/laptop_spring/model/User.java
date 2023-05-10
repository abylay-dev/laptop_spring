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

    public User(Long id, String fullname, String username, String password, String rePassword) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }

    public User(Long id, String fullname, String username) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
    }

    public String getRolesName() {
        for (Role r : roles) {
            if (r.getName().equals("ROLE_ADMIN")) {
                return "Admin";
            } else if (r.getName().equals("ROLE_MODERATOR")) {
                return "Moderator";
            } else {
                return "User";
            }
        }
        return null;
    }
}
