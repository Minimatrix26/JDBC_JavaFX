package Repo;

import Domeniu.Pacient;
import Domeniu.Programare;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ProgramareDbRepo extends MemoryRepo<Programare> implements IDbRepo<Programare> {

    private String JDBC_URL = "jdbc:sqlite:database.db";

    private Connection connection;

    public ProgramareDbRepo() {
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
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createTable() {
        try(final Statement stmt = connection.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS programari(id int, pacient_id int, date Date, scop varchar(255));");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initTable() {
        ArrayList<Programare> programari = new ArrayList<>();
        programari.add(new Programare(1, new Pacient(1, "Ion", "Pop"), new Date(), "Consultatie"));
        programari.add(new Programare(2, new Pacient(2, "Ion", "B-Low"), new Date(), "Tratament"));
        programari.add(new Programare(3, new Pacient(3, "Hatz", "Jonathan"), new Date(), "Analize"));
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO programari VALUES (?,?,?,?);")) {
            for (Programare p : programari) {
                stmt.setInt(1, p.getId());
                stmt.setInt(2, p.getPacient().getId());
                stmt.setDate(3, new java.sql.Date(p.getData().getTime()));
                stmt.setString(4, p.getScop());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Programare> getAll(){
        ArrayList<Programare> programari = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM programari;")){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Pacient pacient = getById(rs.getInt(2)).getPacient();
                Programare p = new Programare(rs.getInt(1), pacient, rs.getDate(3), rs.getString(4));
                programari.add(p);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return programari;
    }

    public void add(Programare p){
        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO programari values (?,?,?,?);")){
            stmt.setInt(1, p.getId());
            stmt.setInt(2, p.getPacient().getId());
            stmt.setDate(3, new java.sql.Date(p.getData().getTime()));
            stmt.setString(4, p.getScop());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM programari WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Programare newProgramare) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE programari SET pacient_id=?, date=?, scop=? WHERE id=?;")) {
            stmt.setInt(1, newProgramare.getPacient().getId());
            stmt.setDate(2, new java.sql.Date(newProgramare.getData().getTime()));
            stmt.setString(3, newProgramare.getScop());
            stmt.setInt(4, newProgramare.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

