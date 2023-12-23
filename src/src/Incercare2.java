import Domeniu.Pacient;
import Domeniu.Programare;
import Repo.IDbRepo;
import Repo.PacientDbRepo;
import Repo.ProgramareDbRepo;
import Service.Service;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Incercare2 extends Application {
    private Scene pacientScene, programareScene;
    @Override
    public void start(Stage stage) throws Exception {
        IDbRepo<Pacient> pacientIDbRepo = new PacientDbRepo();
        IDbRepo<Programare> programareIDbRepo = new ProgramareDbRepo();
        Service service = new Service((PacientDbRepo) pacientIDbRepo, (ProgramareDbRepo) programareIDbRepo);

        VBox mainVerticalBox = new VBox();
        mainVerticalBox.setPadding(new Insets(10));

        ListView<Pacient> pacientListView = new ListView<>();
        ObservableList<Pacient> pacients = FXCollections.observableArrayList(service.getAllPacienti());
        pacientListView.setItems(pacients);
        mainVerticalBox.getChildren().add(pacientListView);

        GridPane pacientGridPane = new GridPane();
        pacientGridPane.setPadding(new Insets(10,0,0,0));
        Label idLabel = new Label("ID");
        idLabel.setPadding(new Insets(0,10,0,0));
        TextField idText = new TextField();

        Label numeLabel = new Label("Nume");
        TextField numeText = new TextField();

        Label prenumeLabel = new Label("Prenume");
        TextField prenumeText = new TextField();

        pacientGridPane.add(idLabel, 0, 0);
        pacientGridPane.add(idText, 0, 1);
        pacientGridPane.add(numeLabel, 1, 0);
        pacientGridPane.add(numeText, 1,1);
        pacientGridPane.add(prenumeLabel, 2, 0);
        pacientGridPane.add(prenumeText, 2, 1);

        mainVerticalBox.getChildren().add(pacientGridPane);

        HBox pacientButoaneBox = new HBox();
        pacientButoaneBox.setPadding(new Insets(10,0,0,0));
        Button addPacientButton = new Button("Adauga");
        Button deletePacientButton = new Button("Delete");
        Button updatePacientButton = new Button("Update");
        Button switchToProgramariButton = new Button("Vezi Programari");
        pacientButoaneBox.getChildren().addAll(addPacientButton, deletePacientButton, updatePacientButton, switchToProgramariButton);
        mainVerticalBox.getChildren().add(pacientButoaneBox);

        addPacientButton.setOnMouseClicked(event ->{
            int id = Integer.parseInt(idText.getText());
            String nume = numeText.getText();
            String prenume = prenumeText.getText();
            service.addPacient(id, nume, prenume);
            pacients.setAll(service.getAllPacienti());
        });

        deletePacientButton.setOnMouseClicked(event ->{
            int id = Integer.parseInt(idText.getText());
            service.deletePacient(id);
            pacients.setAll(service.getAllPacienti());
        });

        updatePacientButton.setOnMouseClicked(event ->{
            int id = Integer.parseInt(idText.getText());
            String nume = numeText.getText();
            String prenume = prenumeText.getText();
            service.updatePacient(new Pacient(id, nume, prenume));
            pacients.setAll(service.getAllPacienti());
        });

        switchToProgramariButton.setOnMouseClicked(event -> {
            stage.setScene(programareScene);
        });

        pacientScene = new Scene(mainVerticalBox, 400, 400);

        // de implementat scena pentru Programari (model luat din seminar 5 grupa 324)

        VBox programareVerticalBox = new VBox();
        programareVerticalBox.setPadding(new Insets(10));

        ListView<Programare> programareListView = new ListView<>();
        ObservableList<Programare> programari = FXCollections.observableArrayList(service.getAllProgramari());
        programareListView.setItems(programari);
        programareVerticalBox.getChildren().add(programareListView);

        GridPane programareGridPane = new GridPane();
        programareGridPane.setPadding(new Insets(10, 0, 0, 0));

        Label progIdLabel = new Label("ID");
        TextField progIdText = new TextField();

        Label progPacientID = new Label("Pacient ID");
        TextField progPacientIDText = new TextField();

        Label progDate = new Label("Data");
        DatePicker datePicker = new DatePicker();

        Label progScop = new Label("Scop");
        TextField progScopText = new TextField();

        programareGridPane.add(progIdLabel, 0, 0);
        programareGridPane.add(progIdText, 0, 1);
        programareGridPane.add(progPacientID, 1, 0);
        programareGridPane.add(progPacientIDText, 1, 1);
        programareGridPane.add(progDate, 2, 0);
        programareGridPane.add(datePicker, 2, 1);
        programareGridPane.add(progScop, 3, 0);
        programareGridPane.add(progScopText, 3, 1);

        programareVerticalBox.getChildren().add(programareGridPane);

        HBox programareButoaneBox = new HBox();
        programareButoaneBox.setPadding(new Insets(10, 0, 0, 0));
        Button addProgButton = new Button("Adauga");
        Button updateProgButton = new Button("Update");
        Button deleteProgButton = new Button("Delete");
        Button switchToPacientiButton = new Button("Vezi Pacienti");
        programareButoaneBox.getChildren().addAll(addProgButton, updateProgButton, deleteProgButton, switchToPacientiButton);
        programareVerticalBox.getChildren().add(programareButoaneBox);

        addProgButton.setOnMouseClicked(event -> {
            try {
                int id = Integer.parseInt(progIdText.getText());
                int idPacient = Integer.parseInt(progPacientIDText.getText());
                LocalDate date = datePicker.getValue();
                String scop = progScopText.getText();

//                // Verificăm dacă există un pacient cu ID-ul specificat
//                if (pacientIDbRepo.getById(idPacient) == null) {
//                    System.out.println("Nu există un pacient cu ID-ul specificat!");
//                    return;
//                }
//
//                Pacient selectedPacient = pacientIDbRepo.getById(idPacient);

                Pacient selectedPacient = service.getPacientById(idPacient);

                service.addProgramare(id, selectedPacient, Date.valueOf(date), scop);

                programari.setAll(service.getAllProgramari());
            } catch (NumberFormatException e) {
                System.out.println("Introduceți o valoare validă pentru ID-ul pacientului.");
            }
        });

        updateProgButton.setOnMouseClicked(event -> {
            try {
                int id = Integer.parseInt(progIdText.getText());
                int idPacient = Integer.parseInt(progPacientIDText.getText());
                LocalDate date = datePicker.getValue();
                String scop = progScopText.getText();

                // Verificăm dacă există un pacient cu ID-ul specificat
                if (pacientIDbRepo.getById(idPacient) == null) {
                    System.out.println("Nu există un pacient cu ID-ul specificat!");
                    return;
                }

                Pacient selectedPacient = pacientIDbRepo.getById(idPacient);

                Programare newProg = new Programare(id, selectedPacient, Date.valueOf(date), scop);
                service.updateProgramare(newProg);
                programari.setAll(service.getAllProgramari());
            } catch (NumberFormatException e) {
                System.out.println("Introduceți o valoare validă pentru ID-ul pacientului.");
            }
        });

        deleteProgButton.setOnMouseClicked(event -> {
            try {
                int id = Integer.parseInt(progIdText.getText());
                service.deleteProgramare(id);
                programari.setAll(service.getAllProgramari());
            } catch (NumberFormatException e) {
                System.out.println("Introduceți o valoare validă pentru ID-ul programării.");
            }
        });

        switchToPacientiButton.setOnMouseClicked(event -> {
            stage.setScene(pacientScene);
        });

        programareScene = new Scene(programareVerticalBox, 400, 400);



        stage.setTitle("Aplicatie idk");
        stage.setScene(pacientScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
