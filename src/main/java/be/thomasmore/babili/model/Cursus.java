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
    private String beschrijving;
    private Integer docentId;

    public Cursus() {
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

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Integer getDocentId() {
        return docentId;
    }

    public void setDocentId(Integer docentId) {
        this.docentId = docentId;
    }
}
