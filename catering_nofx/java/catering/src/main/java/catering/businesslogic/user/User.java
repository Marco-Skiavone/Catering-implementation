package catering.businesslogic.user;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class User {

    private static Map<Integer, User> loadedUsers = new HashMap<Integer, User>();

    protected int id;
    protected String username;

    public User() {
        id = 0;
        username = "";
    }

    public String getUserName() {
        return username;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return username;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid)) return loadedUsers.get(uid);

        User load = new User();
        String userQuery = "SELECT * FROM Users WHERE id='"+uid+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.username = rs.getString("username");
            }
        });
        if (load.id > 0) {
            loadedUsers.put(load.id, load);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + load.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    User loadWRole;
                    loadedUsers.remove(load.id);
                    switch (role.charAt(0)) {
                        case 'c':
                            loadWRole = new Cook(load.id, load.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 'h':
                            loadWRole = new Chef(load.id, load.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 'o':
                            loadWRole = new Organizer(load.id, load.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 's':
                            loadWRole = new ServiceStaff(load.id, load.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                    }
                }
            });
            return loadedUsers.get(load.id);
        } else
            return load;
    }

    public static User loadUser(String username) {
        User u = new User();
        String userQuery = "SELECT * FROM Users WHERE username='"+username+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                u.id = rs.getInt("id");
                u.username = rs.getString("username");
            }
        });
        if (u.id > 0) {
            loadedUsers.put(u.id, u);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    User loadWRole;
                    loadedUsers.remove(u.id);
                    switch (role.charAt(0)) {
                        case 'c':
                            loadWRole = new Cook(u.id, u.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 'h':
                            loadWRole = new Chef(u.id, u.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 'o':
                            loadWRole = new Organizer(u.id, u.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                            break;
                        case 's':
                            loadWRole = new ServiceStaff(u.id, u.username);
                            loadedUsers.put(loadWRole.id, loadWRole);
                    }
                }
            });
            return loadedUsers.get(u.id);
        } else
            return u;
    }

    public boolean isChef() {
        return false;
    }

    public boolean isOrganizer() {
        return false;
    }

    public boolean isStaff() {
        return false;
    }

    public boolean isCook() {
        return false;
    }

    public boolean isServiceStaff() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(username, user.username);
    }
}
