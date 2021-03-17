package Dao;

import Model.*;
import Services.Fill;
import Services.Person;
import com.google.gson.Gson;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao {
    private final Connection conn;

    private GenerationHelp fNames;
    private GenerationHelp mNames;
    private GenerationHelp sNames;

    public PersonDao(Connection conn)
    {
        this.conn = conn;
    }

    public void insert(PersonModel person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO person (personID, username, firstName, lastName, gender, fatherID, motherId, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getuser());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database  " + e.getMessage());
        }
    }

    public PersonModel find(String personID) throws DataAccessException {
        PersonModel person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new PersonModel(rs.getString("personID"), rs.getString("username"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherId"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public ArrayList<PersonModel> findAll(String userID) throws DataAccessException, SQLException {
        String sql = "select personID, username, firstName, lastName, gender, fatherID, motherId, spouseID from person";
        ArrayList<PersonModel> pm = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                String personID = rs.getString(1);
                String username = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherId = rs.getString(7);
                String spouseID = rs.getString(8);

                PersonModel person = new PersonModel(personID, username, firstName, lastName, gender, fatherID, motherId, spouseID);
                pm.add(person);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        //Nate has here that if (stmt != null) then close it, but I'm not sure what it means or why it's not working
        }
        return pm;
    }

    public ArrayList<PersonModel> findAllWithUser(String username) throws DataAccessException, SQLException {
        ArrayList<PersonModel> peopleWithUser = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select personID, username, firstName, lastName, gender, fatherID, motherId, spouseID from person WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String personID = rs.getString(1);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherId = rs.getString(7);
                String spouseID = rs.getString(8);

                PersonModel person = new PersonModel(personID, username, firstName, lastName, gender, fatherID, motherId, spouseID);
                peopleWithUser.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return peopleWithUser;
    }

    void clearPeople() throws DataAccessException {
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting from database (clear people)");
        }
    }

    public String randomMaleName() {
        return mNames.getRandom();
    }

    public String randomFemaleName() {
        return fNames.getRandom();
    }

    public String randomSurName() {
        return sNames.getRandom();
    }

    public void generatePeopleNames() throws FileNotFoundException {
        Gson g = new Gson();
        try {
            File myObj = new File("json\\filename.txt");
            BufferedReader br = new BufferedReader(new FileReader(FileSystems.getDefault().getPath("fnames.json").toFile()));
            fNames = g.fromJson(br, GenerationHelp.class);
            BufferedReader Br = new BufferedReader(new FileReader(FileSystems.getDefault().getPath("mnames.json").toFile()));
            mNames = g.fromJson(Br, GenerationHelp.class);
            BufferedReader BR = new BufferedReader(new FileReader(FileSystems.getDefault().getPath("snames.json").toFile()));
            sNames = g.fromJson(BR, GenerationHelp.class);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    public PersonModel personFromUser(UserModel user) throws DataAccessException {
        try {
            Fill f = new Fill();
            PersonModel person = new PersonModel(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), f.generatePersonID(), f.generatePersonID(), null);
            insert(person);
            return person;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFromUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM person WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting from username");
        }
    }

}


