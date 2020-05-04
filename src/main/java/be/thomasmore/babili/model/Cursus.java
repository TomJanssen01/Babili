package be.thomasmore.babili.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cursus {
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
