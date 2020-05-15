package be.thomasmore.babili.model;

import javax.persistence.*;

@Entity
public class Opdracht {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opdracht_generator")
    @SequenceGenerator(name = "opdracht_generator", sequenceName = "opdracht_seq",
            initialValue = 0, allocationSize = 1)
    @Id
    private Integer Id;
    private String titel;
    private String opgave;
    private String voorbeeld;
    @OneToOne
    private Cursus cursus;

    public Opdracht() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getOpgave() {
        return opgave;
    }

    public void setOpgave(String opgave) {
        this.opgave = opgave;
    }

    public String getVoorbeeld() {
        return voorbeeld;
    }

    public void setVoorbeeld(String voorbeeld) {
        this.voorbeeld = voorbeeld;
    }

//    public String getBeoordeling() {
//        return beoordeling;
//    }
//
//    public void setBeoordeling(String beoordeling) {
//        this.beoordeling = beoordeling;
//    }

    public Cursus getCursus() {
        return cursus;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }
}
