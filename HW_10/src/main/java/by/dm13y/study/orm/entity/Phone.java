package by.dm13y.study.orm.entity;


import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone", schema = "public")
public class Phone {
    @Id
    @GeneratedValue
    @Getter @Setter
    @Column(name="id",nullable = false, updatable = false)
    private long id;

    @Getter @Setter
    @Column(name="number",nullable = false, updatable = false)
    private String number;

    public Phone(String number) {
        this.number = number;
    }

}
