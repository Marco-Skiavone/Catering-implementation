package catering.businesslogic.user;

public class Chef extends Role {
    public Chef(AbstractUser component) {
        super(component);
    }

    @Override
    public boolean isChef() {
        return true;
    }

    @Override
    public String toString() {
        return "Chef(" + super.toString() + ")";
    }
}
