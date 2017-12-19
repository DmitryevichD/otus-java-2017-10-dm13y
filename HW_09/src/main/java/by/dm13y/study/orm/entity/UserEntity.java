package by.dm13y.study.orm.entity;

import by.dm13y.study.annotations.Column;
import by.dm13y.study.annotations.Id;
import by.dm13y.study.annotations.Table;

@Table(name = "user")
public class UserEntity implements Entity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
