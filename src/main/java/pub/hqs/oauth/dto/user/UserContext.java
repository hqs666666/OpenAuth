package pub.hqs.oauth.dto.user;

public class UserContext implements AutoCloseable {
    static final ThreadLocal<CurrentUser> current = new ThreadLocal<>();

    public UserContext(CurrentUser user) {
        current.set(user);
    }

    public static CurrentUser getCurrentUser() {
        return current.get();
    }

    public void close() {
        current.remove();
    }
}
