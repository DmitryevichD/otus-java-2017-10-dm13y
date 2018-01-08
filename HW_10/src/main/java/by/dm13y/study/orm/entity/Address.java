package by.dm13y.study.orm.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address", schema = "public")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name="id", nullable = false, updatable = false)
    private long id;

    @Getter @Setter
    @Column(name="street", nullable = false, updatable = false)
    private String street;

    public Address(String street) {
        this.street = street;
    }
}
