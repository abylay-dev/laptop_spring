package kz.spring.laptop_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
    //ROLE_<NAME_ROLE>      ROLE_ADMIN      ROLE_USER       ROLE_MODERATOR
}
