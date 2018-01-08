package by.dm13y.study.orm.entity;


import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "phone", schema = "public")
public class Phone implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name="id",nullable = false, updatable = false)
    private long id;

    @Getter @Setter
    @Column(name="number",nullable = false)
    private String number;

    public Phone(String number) {
        this.number = number;
    }

}
