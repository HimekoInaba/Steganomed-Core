package kz.stegano.med.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authority;
    private String description;

    public enum roles {
        DOCTOR,
        PATIENT
    }

    public Role(String authority) {
        setAuthority(authority);
    }

    public void setAuthority(String authority) {
        Objects.requireNonNull(authority);
        this.authority = authority;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != null && role.id != null) {
            return id.equals(role.id);
        } else {
            return authority.equals(role.authority);
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + authority.hashCode();
        return result;
    }

    public String getCode() {
        return this.authority;
    }

}
