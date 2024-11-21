package catering.businesslogic.user;

import java.util.*;

public class UserManager {
    private AbstractUser currentUser;
    private HashSet<UserEventReceiver> eventReceivers;
    public UserManager() {
        eventReceivers = new HashSet<>();
    }

    public void fakeLogin(String username) {
        this.currentUser = AbstractUser.loadUser(username);
    }

    public AbstractUser getCurrentUser() {
        return this.currentUser;
    }

    // --- Event methods ---

    /**
     * @throws IllegalArgumentException if argument passed is null.
     * */
    public void addEventReceiver(UserEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        eventReceivers.add(er);
    }

    /**
     * @throws IllegalArgumentException if argument passed is null or not present in the set. */
    public void removeEventReceiver(UserEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        if (!eventReceivers.remove(er))
            throw new IllegalArgumentException("Receiver does not exist!");
    }

}
