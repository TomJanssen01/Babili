package be.thomasmore.babili.model;

import javax.persistence.*;

@Entity
public class Opdracht {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opdracht_generator")
    @SequenceGenerator(name = "opdracht_generator", sequenceName = "opdracht_seq",
            initialValue = 0, allocationSize = 1)
    @Id
    private Integer id;
    private String title;
    private String task;
    private String example;
    private String review;
    @ManyToOne (fetch = FetchType.LAZY)
    private Cursus cursus;
    @ManyToOne (fetch = FetchType.LAZY)
    private Inlevering inlevering;


    public Opdracht() {

    }

    @Override
    public String toString() {
        return "Opdracht{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", task='" + task + '\'' +
                ", example='" + example + '\'' +
                ", review='" + review + '\'' +
                '}';
    }

    public Inlevering getInlevering() {
        return inlevering;
    }

    public void setInlevering(Inlevering inlevering) {
        this.inlevering = inlevering;
    }

    public Cursus getCursus() {
        return cursus;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
