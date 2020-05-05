package be.thomasmore.babili.model;

import javax.persistence.*;

@Entity
public class Inlevering {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inlevering_generator")
    @SequenceGenerator(name = "inlevering_generator", sequenceName = "inlevering_seq",
            initialValue = 0, allocationSize = 1)
    @Id
    private Integer Id;
    private String audioPath;
    @OneToOne
    private Opdracht opdracht;
    @OneToOne
    private User user;

    public Inlevering() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Opdracht getOpdracht() {
        return opdracht;
    }

    public void setOpdracht(Opdracht opdracht) {
        this.opdracht = opdracht;
    }
}
