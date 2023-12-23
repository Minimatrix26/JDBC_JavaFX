package Repo;

import Domeniu.Pacient;

import java.sql.*;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

public class PacientDbRepo extends MemoryRepo<Pacient> implements IDbRepo<Pacient> {

    private String JDBC_URL = "jdbc:sqlite:database.db";

    private Connection connection;

    public PacientDbRepo() {
        openConnection();
        createTable();
        //initTable();
    }

    @Override
    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);
        try{
            if(connection == null || connection.isClosed()){
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createTable() {
        try(final Statement stmt = connection.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS patients(id int, nume varchar(30), prenume varchar(30));");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initTable() {
        ArrayList<Pacient> pacients = new ArrayList<>();
        pacients.add(new Pacient(1, "Ion", "Pop"));
        pacients.add(new Pacient(2, "Ion", "B-Low"));
        pacients.add(new Pacient(3, "Hatz", "Jonathan"));
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO patients VALUES (?,?,?);")){
            for (Pacient p: pacients){
                stmt.setInt(1, p.getId());
                stmt.setString(2, p.getNume());
                stmt.setString(3, p.getPrenume());
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Pacient> getAll(){
        ArrayList<Pacient> pacienti = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement("SELECT * from patients;")){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Pacient p = new Pacient(rs.getInt(1), rs.getString(2), rs.getString(3));
                pacienti.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pacienti;
    }

    public void add(Pacient p){
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO patients values (?,?,?);")){
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getNume());
            stmt.setString(3, p.getPrenume());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM patients WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Pacient newPacient) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE patients SET nume=?, prenume=? WHERE id=?;")) {
            stmt.setString(1, newPacient.getNume());
            stmt.setString(2, newPacient.getPrenume());
            stmt.setInt(3, newPacient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


