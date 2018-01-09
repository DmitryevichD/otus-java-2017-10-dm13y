package by.dm13y.study.orm.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name="number",nullable = false, unique = true)
    private String number;

    @Getter @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Phone(){}
    public Phone(String number, User user) {
        this.number = number;
        this.user = user;
    }

}
