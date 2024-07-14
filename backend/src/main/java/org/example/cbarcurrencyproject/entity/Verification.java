package org.example.cbarcurrencyproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Verification extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 6)
    private String verificationCode;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        return Objects
                .hash(id, user, verificationCode);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Verification verification = (Verification) obj;

        return Objects.deepEquals(id, verification.id)
                && Objects.deepEquals(verificationCode, verification.verificationCode)
                && Objects.deepEquals(user, verification.user)
                && Objects.deepEquals(this.getCreatedAt(), verification.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), verification.getUpdatedAt());
    }
}