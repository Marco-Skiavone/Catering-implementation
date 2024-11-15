package catering.businesslogic.user;

public class Staff extends Role {
    public Staff(User component) {
        super(component);
    }

    @Override
    public boolean isStaff() {
        return true;
    }
}
