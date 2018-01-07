package by.dm13y.study.orm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr", schema = "public")
public class User {
    @Id
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
    @OneToOne
    @PrimaryKeyJoinColumn
    private Address address;

    @Getter @Setter
    @OneToMany
    @JoinColumn(name = "id")
    private List<Phone> phones;

    @ManyToOne
    @JoinColumn(name="department_id")
    @Getter @Setter
    private Department department;

    public User(String name, int age, Address address, Phone phone, Department department) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones.add(phone);
        this.department = department;
    }
}
