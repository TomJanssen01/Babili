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
    private String feedback;
    @OneToOne
    private Opdracht opdracht;

    public Inlevering() {
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Opdracht getOpdracht() {
        return opdracht;
    }

    public void setOpdracht(Opdracht opdracht) {
        this.opdracht = opdracht;
    }
}
