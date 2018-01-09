package by.dm13y.study.orm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr", schema = "public")
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id",nullable = false)
    @Getter @Setter private long id;

    @Column(name = "NAME", nullable = false)
    @Getter @Setter private String name;

    @Column(name = "AGE", nullable = false)
    @Getter @Setter private int age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Getter @Setter private Address address;

    //bidirectional
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Getter @Setter private Set<Phone> phones = new HashSet<>();

    public User(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void addPhone(String number){
        phones.add(new Phone(number, this));
    }
}
