package be.thomasmore.babili.model;

public class Docent {
    private User user;

    public Docent(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Docent{" +
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
