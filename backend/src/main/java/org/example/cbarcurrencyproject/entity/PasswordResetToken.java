package org.example.cbarcurrencyproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String resetPasswordToken;

    @OneToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            },
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getResetPasswordToken(), this.getUser());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        PasswordResetToken passwordResetToken = (PasswordResetToken) object;

        return Objects.deepEquals(this.getId(), passwordResetToken.getId()) &&
                Objects.deepEquals(this.getResetPasswordToken(), passwordResetToken.getResetPasswordToken()) &&
                Objects.deepEquals(this.getUser(), passwordResetToken.getUser());
    }

}