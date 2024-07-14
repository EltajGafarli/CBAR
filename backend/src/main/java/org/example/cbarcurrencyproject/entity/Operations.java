package org.example.cbarcurrencyproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime operationDate;

    @Override
    public int hashCode() {
        return Objects
                .hash(id, operationDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Operations operations)) {
            return false;
        }

        return Objects.equals(this.id, operations.id)
                && Objects.equals(this.operationDate, operations.operationDate);
    }
}
