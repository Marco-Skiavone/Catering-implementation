package catering.businesslogic.user;

public abstract class Role extends AbstractUser {
    private AbstractUser component;

    public Role(AbstractUser component) {
        if (component == null) throw new NullPointerException("component is null");
        this.component = component;
    }

    public AbstractUser getComponent() {
        return component;
    }

    public void setComponent(AbstractUser component) {
        this.component = component;
    }

    @Override
    public void setAll(int id, String username) {
        component.setAll(id, username);
    }

    @Override
    public String toString() {
        return component.toString();
    }

    @Override
    public String getUsername() {
        return component.getUsername();
    }

    @Override
    public int getId() {
        return component.getId();
    }

    // Overriding methods:
    @Override
    public boolean isChef() {
        return component.isChef();
    }

    @Override
    public boolean isOrganizer() {
        return component.isOrganizer();
    }

    @Override
    public boolean isStaff() {
        return component.isStaff();
    }

    @Override
    public boolean isCook() {
        return component.isCook();
    }

    @Override
    public boolean isServiceStaff() {
        return component.isServiceStaff();
    }
}
