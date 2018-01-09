package by.dm13y.study.orm.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone", schema = "public")
@NoArgsConstructor
public class Phone implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id",nullable = false, updatable = false)
    @Getter @Setter private long id;

    @Column(name="number",nullable = false, unique = true)
    @Getter @Setter private String number;

    //bidirectional
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter private User user;

    public Phone(String number, User user) {
        this.number = number;
        this.user = user;
    }

}
