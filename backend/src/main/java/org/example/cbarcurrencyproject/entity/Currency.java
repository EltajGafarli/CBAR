package org.example.cbarcurrencyproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "currency")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(5)")
    private String code;

    @Column(columnDefinition = "varchar(150)")
    private String name;

    private int nominal;

    private double value;


    @Override
    public int hashCode() {
        return Objects
                .hash(id, name, code, nominal, value);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Currency currency)) {
            return false;
        }

        return Objects.equals(this.id, currency.id)
                && Objects.equals(this.code, currency.code)
                && Objects.equals(this.name, currency.name)
                && Objects.equals(this.nominal, currency.nominal)
                && Objects.equals(this.value, currency.value);
    }
}
