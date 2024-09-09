package catering.businesslogic.user;

public class Chef extends User {
    public Chef(int id, String username) {
        super.id = id;
        super.username = username;
    }

    @Override
    public boolean isChef() {
        return true;
    }
}
