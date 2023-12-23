package Service;

import Domeniu.Pacient;
import Domeniu.Programare;
import Repo.PacientDbRepo;
import Repo.ProgramareDbRepo;

import java.sql.Date;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class Service {
    private PacientDbRepo pacientDbRepo;
    private ProgramareDbRepo programareDbRepo;

    public Service(PacientDbRepo pacientDbRepo, ProgramareDbRepo programareDbRepo) {
        this.pacientDbRepo = pacientDbRepo;
        this.programareDbRepo = programareDbRepo;
    }

    public void addPacient(int id, String nume, String prenume){
        Pacient pacient = new Pacient(id, nume, prenume);
        pacientDbRepo.add(pacient);
    }

    public void addProgramare(int id, Pacient pacient, Date data, String scop){
        programareDbRepo.add(new Programare(id, pacient, data, scop));
    }

    public void deletePacient(int id){
        pacientDbRepo.delete(id);
    }

    public void deleteProgramare(int id) {
        programareDbRepo.delete(id);
    }

    public void updatePacient(Pacient newPacient) {
        pacientDbRepo.update(newPacient);
    }

    public void updateProgramare(Programare newProgramare) {
        programareDbRepo.update(newProgramare);
    }

    public ArrayList<Pacient> getAllPacienti(){
        return pacientDbRepo.getAll();
    }

    public ArrayList<Programare> getAllProgramari(){
        return programareDbRepo.getAll();
    }

    public Programare getProgById(int idProgramare){
        return programareDbRepo.getById(idProgramare);
    }

    public Pacient getPacientById(int idPacient){
        return pacientDbRepo.getById(idPacient);
    }

    public List<Pacient> getNumarProgramariPerPacient() {
        Map<Pacient, Long> numarProgramariPerPacient = programareDbRepo.getAll().stream()
                .collect(Collectors.groupingBy(Programare::getPacient, Collectors.counting()));

        return numarProgramariPerPacient.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
