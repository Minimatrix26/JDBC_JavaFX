package Domeniu;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Programare extends Entity {

    private static final long serialVersionUID = 1L;
    private Pacient pacient;
    private Date data;
    //private String ora;
    private String scop;

    public Programare(int id, Pacient pacient, Date data, String scop) {
        super(id);
        this.pacient = pacient;
        this.data = data;
        this.scop = scop;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


    public String getScop() {
        return scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", pacient=" + pacient +
                ", data="+ new SimpleDateFormat("dd-MM-yyyy").format(data) +
                ", scop='" + scop + '\'' +
                '}';
    }
}
