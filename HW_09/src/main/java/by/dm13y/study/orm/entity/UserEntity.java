package by.dm13y.study.orm.entity;

import by.dm13y.study.annotations.Column;

public class UserEntity extends DataSet {
    private final static String tableName = "user";
    private final static String idColumnName = "id";

    public UserEntity(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getIdColumnName() {
        return idColumnName;
    }


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
}
