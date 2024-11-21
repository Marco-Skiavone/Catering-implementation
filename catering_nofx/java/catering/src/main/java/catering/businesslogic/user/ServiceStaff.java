package catering.businesslogic.user;

public class ServiceStaff extends Staff {
    public ServiceStaff(AbstractUser component) {
        super(component);
    }

    @Override
    public boolean isServiceStaff() {
        return true;
    }

    @Override
    public String toString() {
        return "ServiceStaff(" + getComponent().toString() + ")";
    }
}
