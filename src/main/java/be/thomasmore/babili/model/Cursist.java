package be.thomasmore.babili.model;

public class Cursist {
    private User user;

    public Cursist(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cursist{" +
                "user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
