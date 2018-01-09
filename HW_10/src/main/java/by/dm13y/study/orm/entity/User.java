package by.dm13y.study.orm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr", schema = "public")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name="id",nullable = false)
    private long id;

    @Getter @Setter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter @Setter
    @Column(name = "AGE", nullable = false)
    private int age;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Address address;

    @Getter
    @Setter
    //bidirectional
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<>();

    public User(){}
    public User(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void addPhone(String number){
        phones.add(new Phone(number, this));
    }
}
