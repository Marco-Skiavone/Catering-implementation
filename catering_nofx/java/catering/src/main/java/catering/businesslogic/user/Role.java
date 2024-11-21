package catering.businesslogic.user;

public class Role extends AbstractUser {
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
