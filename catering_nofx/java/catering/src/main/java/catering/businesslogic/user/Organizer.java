package catering.businesslogic.user;

public class Organizer extends Role {
    public Organizer(AbstractUser component) {
        super(component);
    }

    @Override
    public boolean isOrganizer() {
        return true;
    }

    @Override
    public String toString() {
        return "Organizer(" + super.toString() + ")";
    }
}
