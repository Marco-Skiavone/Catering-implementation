package catering.businesslogic.user;

public class Worker extends User {

    public Worker() {
        super.id = 0;
        super.username = "";
    }

    public Worker(int id, String username) {
        super.id = id;
        super.username = username;
    }

    @Override
    public void setAll(int id, String username) {
        super.id = id;
        super.username = username;
    }

    @Override
    public boolean isChef() {
        return false;
    }

    @Override
    public boolean isOrganizer() {
        return false;
    }

    @Override
    public boolean isStaff() {
        return false;
    }

    @Override
    public boolean isCook() {
        return false;
    }

    @Override
    public boolean isServiceStaff() {
        return false;
    }
}
