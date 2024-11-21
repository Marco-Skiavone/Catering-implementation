package catering.businesslogic.user;

public class UserManager {
    private AbstractUser currentUser;

    public void fakeLogin(String username) {
        this.currentUser = AbstractUser.loadUser(username);
    }

    public AbstractUser getCurrentUser() {
        return this.currentUser;
    }
}
