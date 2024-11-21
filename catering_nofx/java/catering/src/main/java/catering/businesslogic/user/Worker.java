package catering.businesslogic.user;

public class Worker extends AbstractUser {
    private int id;
    private String username;

    public Worker() {
        this.id = 0;
        this.username = "";
    }

    @Override
    public void setAll(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public int getId() {return this.id;}

    @Override
    public String getUsername() {return this.username;}

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
