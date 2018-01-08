package by.dm13y.study.orm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr", schema = "public")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name="id",nullable = false, updatable = false)
    private long id;

    @Getter @Setter
    @Column(name = "NAME", nullable = false)
    private String name;

    @Getter @Setter
    @Column(name = "AGE", nullable = false)
    private int age;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @Getter
    @Setter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private List<Phone> phones = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dep_id")
    @Getter @Setter
    private Department dep;

    public User(String name, int age, Address address, Phone phone, Department department) {
        this.name = name;
        this.age = age;
        this.address = address;
        phones.add(phone);
        this.dep = department;
    }
}
