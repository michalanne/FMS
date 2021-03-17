package Dao;

import Model.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class EventDao {
    private final Connection conn;
    private LocationList locations;

    public EventDao(Connection conn)
    {
        this.conn = conn;
    }

    public void insert(EventModel event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO event (eventID, user, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUser());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public String generateEventID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public EventModel find(String eventID) throws DataAccessException {
        EventModel event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new EventModel(rs.getString("eventID"), rs.getString("user"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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

    void clearEvents() throws DataAccessException {
        String sql = "DROP TABLE event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public ArrayList<EventModel> findAllWithUser(String username) throws DataAccessException, SQLException {
        ArrayList<EventModel> eventsWithUser = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select * from event WHERE user = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String eventID = rs.getString(1);
                String personID = rs.getString(3);
                float latitude = rs.getFloat(4);
                float longitude = rs.getFloat(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                Integer year = rs.getInt(9);

                EventModel event = new EventModel(eventID, username, personID, latitude, longitude, country, city, eventType, year);
                eventsWithUser.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventsWithUser;
    }

    public void generateLocations() throws FileNotFoundException {
        Gson g = new Gson();
        try {
            BufferedReader Br = new BufferedReader(new FileReader(FileSystems.getDefault().getPath("locations.json").toFile()));
            locations = g.fromJson(Br, LocationList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    LocationHelp getRandomLocation() { return locations.getRandom(); } //returns the object, not a string or anything.

    public String generateBirth(String personID, String user, int year) throws DataAccessException {
        EventModel birth = null;
        try {
            generateLocations();
            LocationHelp location = getRandomLocation();
            String eventID = generateEventID();
             birth = new EventModel(eventID, user, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "Birth", year);
            insert(birth);
        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return birth.getEventID();
    }

    public EventModel findFromPersonID(String personID) throws DataAccessException {
        EventModel event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new EventModel(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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

    public void generateDeath(String personID, String birthID, int year, String user) throws DataAccessException, FileNotFoundException {
        Database db = new Database();
        try {
            generateLocations();
            LocationHelp location = getRandomLocation();
            EventModel death = new EventModel(generateEventID(), user, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "Death", year);
            insert(death);
        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateMarriage(String personID, String spouseID, String user, int year) {
        try {
            generateLocations();
            LocationHelp location = getRandomLocation(); //destination wedding
            EventModel marriageWoman = new EventModel(generateEventID(), user, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "Marriage", year);
            EventModel marriageMan = new EventModel(generateEventID(), user, spouseID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "Marriage", year);
            insert(marriageMan);
            insert(marriageWoman);
        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateOtherEvent(String personID, String eventType, String user, int year) throws FileNotFoundException {
        try {
            generateLocations();
            LocationHelp location = getRandomLocation();
            String eventID = generateEventID();
            EventModel event = new EventModel(eventID, user, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
            insert(event);
        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public float longitudeFromID(String eventID) {
        ResultSet rs = null;
        String sql = "SELECT longitude FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            return rs.getFloat(1);
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public float latitudeFromID(String eventID) {
        ResultSet rs = null;
        String sql = "SELECT latitude FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            return rs.getFloat(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public String cityFromID(String eventID) {
        ResultSet rs = null;
        String sql = "SELECT city FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            return rs.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String countryFromID(String eventID) {
        ResultSet rs = null;
        String sql = "SELECT country FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            return rs.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}


