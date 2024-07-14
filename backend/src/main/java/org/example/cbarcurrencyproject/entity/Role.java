package org.example.cbarcurrencyproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.cbarcurrencyproject.entity.enums.RoleEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "roles")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }
    )
    @JsonIgnore
    private User user;

    @Override
    public int hashCode() {
        return Objects
                .hash(this.roleId, this.role,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Role roles = (Role) obj;
        return Objects
                .deepEquals(this.role, roles.role)
                && Objects.deepEquals(this.roleId, roles.roleId)
                && Objects.deepEquals(this.getCreatedAt(), roles.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), roles.getUpdatedAt());
    }
}