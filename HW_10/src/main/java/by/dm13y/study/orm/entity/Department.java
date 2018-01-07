package by.dm13y.study.orm.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "department", schema = "public")
public class Department {
    @Id
    @GeneratedValue
    @Getter @Setter
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Getter @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @OneToMany(mappedBy = "department")
    public Set<User> usersList;

    public Department(String name) {
        this.name = name;
    }

}
