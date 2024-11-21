package catering.businesslogic.user;

public class Staff extends Role {
    public Staff(AbstractUser component) {
        super(component);
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String toString() {
        return "Staff(" + getComponent().toString() + ")";
    }
}
