package by.dm13y.study.auth;

public class UserRoles {
    public static boolean checkCacheAccess(String id) {
        return Long.parseLong(id) > 0;
    }
}
