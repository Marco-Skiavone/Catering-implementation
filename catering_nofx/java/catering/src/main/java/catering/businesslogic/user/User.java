package catering.businesslogic.user;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class User {

    private static Map<Integer, User> loadedUsers = new HashMap<Integer, User>();

    protected int id;
    protected String username;

    public String getUserName() {
        return username;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return username;
    }

    public abstract void setAll(int id, String username);

    // STATIC METHODS FOR PERSISTENCE

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid))
            return loadedUsers.get(uid);
        User u = new Worker();
        String userQuery = "SELECT * FROM Users WHERE id='"+uid+"'";
        PersistenceManager.executeQuery(userQuery, rs ->
                u.setAll(rs.getInt("id"), rs.getString("username")));
        return decorateUser(u);
    }

    public static User loadUser(String username) {
        User u = new Worker();
        String userQuery = "SELECT * FROM Users WHERE username='"+username+"'";
        PersistenceManager.executeQuery(userQuery,
                rs -> u.setAll(rs.getInt("id"), rs.getString("username")));
        return decorateUser(u);
    }

    private static User decorateUser(User u) {
        if (u.id > 0) {
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    User decoratedUser = u;
                    while (rs.next()) {
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
                    }
                    loadedUsers.put(decoratedUser.id, decoratedUser);
                }
            });
            return loadedUsers.get(u.id);
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(username, user.username);
    }
}
