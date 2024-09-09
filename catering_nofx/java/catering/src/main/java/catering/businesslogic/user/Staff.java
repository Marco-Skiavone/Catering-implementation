package catering.businesslogic.user;

public class Staff extends User {
    public Staff(int id, String username) {
        super.id = id;
        super.username = username;
    }

    @Override
    public boolean isStaff() {
        return true;
    }
}
