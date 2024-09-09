package catering.businesslogic.user;

public class ServiceStaff extends Staff {
    public ServiceStaff(int id, String username) {
        super(id, username);
    }

    @Override
    public boolean isServiceStaff() {
        return true;
    }
}
