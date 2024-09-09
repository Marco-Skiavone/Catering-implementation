package catering.businesslogic.user;

public class Organizer extends User {
    public Organizer(int id, String username) {
        super.id = id;
        super.username = username;
    }

    @Override
    public boolean isOrganizer() {
        return true;
    }

}
