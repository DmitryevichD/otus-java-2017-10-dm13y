package by.dm13y.study.orm.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "address", schema = "public")
@NoArgsConstructor
public class Address implements Serializable, Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false, updatable = false)
    @Getter @Setter private long id;

    @Column(name="street", nullable = false, updatable = false)
    @Getter @Setter private String street;

    public Address(String street) {
        this.street = street;
    }
}
