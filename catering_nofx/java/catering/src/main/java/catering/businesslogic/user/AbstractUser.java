package catering.businesslogic.user;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractUser {

    private static final Map<Integer, AbstractUser> loadedUsers = new HashMap<>();

    public abstract int getId();

    public abstract String getUsername();

    public abstract void setAll(int id, String username);

    // STATIC METHODS FOR PERSISTENCE

    public static AbstractUser loadUserById(int uid) {
        if (loadedUsers.containsKey(uid))
            return loadedUsers.get(uid);
        AbstractUser u = new Worker();
        String userQuery = "SELECT * FROM Users WHERE id='"+uid+"'";
        PersistenceManager.executeQuery(userQuery, rs ->
                u.setAll(rs.getInt("id"), rs.getString("username")));
        return decorateUser(u);
    }

    public static AbstractUser loadUser(String username) {
        AbstractUser u = new Worker();
        String userQuery = "SELECT * FROM Users WHERE username='"+username+"'";
        PersistenceManager.executeQuery(userQuery,
                rs -> u.setAll(rs.getInt("id"), rs.getString("username")));
        return decorateUser(u);
    }

    private static AbstractUser decorateUser(AbstractUser u) {
        if (u.getId() > 0) {
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.getId();
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    AbstractUser decoratedUser = u;
                    if (rs.getRow() > 0)
                        do {
                            // "while" used to retrieve every role of a user
                            String role = rs.getString("role_id");
                            switch (role.charAt(0)) {
                                case 'c':
                                    decoratedUser = new Cook(decoratedUser);
                                    break;
                                case 'h':
                                    decoratedUser = new Chef(decoratedUser);
                                    break;
                                case 'o':
                                    decoratedUser = new Organizer(decoratedUser);
                                    break;
                                case 's':
                                    decoratedUser = new ServiceStaff(decoratedUser);
                                default:
                                    System.err.println("Unknown role '" + role + "'");
                            }
                        } while (rs.next());
                    loadedUsers.put(decoratedUser.getId(), decoratedUser);
                }
            });
            return loadedUsers.get(u.getId());
        } else
            return u;
    }

    public abstract boolean isChef();

    public abstract boolean isOrganizer();

    public abstract boolean isStaff();

    public abstract boolean isCook();

    public abstract boolean isServiceStaff();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUser)) return false;
        AbstractUser user = (AbstractUser) o;
        return getId() == user.getId() && Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public String toString() {
        return "<" + getId() + ", " + getUsername() + '>';
    }
}
