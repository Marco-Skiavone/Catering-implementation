package catering.businesslogic.user;

public class Cook extends Staff {
    public Cook(int id, String username) {
        super(id, username);
    }

    @Override
    public boolean isCook() {
        return true;
    }
}
