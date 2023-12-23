package Domeniu;

import java.util.Objects;

public class Pacient extends Entity {

    private static final long serialVersionUID = 1L;
    private String nume;
    private String prenume;

    public Pacient(){
        super(0);
    }

    public Pacient(int id, String nume, String prenume){
        super(id);
        this.nume = nume;
        this.prenume = prenume;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "id=" + id +
                ", nume='" + nume + "'" +
                ", prenume='" + prenume + '\'' +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

}
