package catering.businesslogic.user;

public class Cook extends Staff {
    public Cook(AbstractUser component) {
        super(component);
    }

    @Override
    public boolean isCook() {
        return true;
    }

    @Override
    public String toString() {
        return "Cook(" + super.toString() + ")";
    }
}
