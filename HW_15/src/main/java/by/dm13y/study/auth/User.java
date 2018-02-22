package by.dm13y.study.auth;

import lombok.Getter;
import java.util.Random;

@Getter
public class User {
    private long id;
    private String name;
    private String pwd;
    public User(String name, String pwd){
        this.name = name;
        this.pwd = pwd;
        id = new Random().nextInt(40);
    }
}
