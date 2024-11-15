package catering.businesslogic.user;

public class ServiceStaff extends Staff {
    public ServiceStaff(User component) {
        super(component);
    }

    @Override
    public boolean isServiceStaff() {
        return true;
    }
}
