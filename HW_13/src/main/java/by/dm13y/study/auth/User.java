package by.dm13y.study.auth;

public class User {
    private final String login;
    private final String passw;

    public User(String login, String passw) {
        this.login = login;
        this.passw = passw;
    }

    public boolean isAdmin(){
        if((login.equals("admin")) && (passw.equals(login))){
            return true;
        }else return false;
    }
}
