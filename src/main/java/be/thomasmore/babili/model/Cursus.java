package be.thomasmore.babili.model;

import javax.persistence.*;

@Entity
public class Cursus {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cursus_generator")
    @SequenceGenerator(name = "cursus_generator", sequenceName = "cursus_seq",
            initialValue = 0, allocationSize = 1)
    @Id
    private Integer id;
    private String naam;
    private String description;

    public Cursus() {
    }

    @Override
    public String toString() {
        return "Cursus{" +
                "id=" + id +
                ", name='" + naam + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String name) {
        this.naam = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
