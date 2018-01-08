package by.dm13y.study.orm.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "department", schema = "public")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Getter @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @OneToMany(mappedBy = "dep")
    private List<User> users = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

}
