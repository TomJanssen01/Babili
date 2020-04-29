package be.thomasmore.babili.model;

import javax.persistence.Entity;


public class Cursist {
    private BabiliUser user;

    public Cursist(BabiliUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cursist{" +
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
