package by.dm13y.study.auth;

public class UserChecker {
    public static boolean isValid(User user) {
        return user.getName().equals("admin") && user.getPwd().equals("admin");
    }

    public static boolean isValid(String userId) {
        return Long.parseLong(userId) < 100;
    }
}
