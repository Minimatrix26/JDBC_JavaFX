import Domeniu.Pacient;
import Repo.IDbRepo;
import Repo.PacientDbRepo;

import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        IDbRepo<Pacient> pacientIDbRepo = new PacientDbRepo();

        ArrayList<Pacient> pacients = pacientIDbRepo.getAll();

        for(Pacient p: pacients){
            System.out.println(p);
        }
    }
}