package catering.businesslogic.user;

public class Chef extends Role {
    public Chef(User component) {
        super(component);
    }

    @Override
    public boolean isChef() {
        return true;
    }
}
