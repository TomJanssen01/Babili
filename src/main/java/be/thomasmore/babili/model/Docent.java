package be.thomasmore.babili.model;

public class Docent {
    private BabiliUser user;

    public Docent(BabiliUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Docent{" +
                "user=" + user +
                '}';
    }

    public BabiliUser getUser() {
        return user;
    }

    public void setUser(BabiliUser user) {
        this.user = user;
    }
}
