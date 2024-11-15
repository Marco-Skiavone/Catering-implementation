package catering.businesslogic.user;

public class Cook extends Staff {
    public Cook(User component) {
        super(component);
    }

    @Override
    public boolean isCook() {
        return true;
    }
}
