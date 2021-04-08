package Services;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Dao.UserDao;
import Model.PersonModel;
import Model.UserModel;
import Request.FillRequest;
import Result.FillResult;
import Dao.EventDao;

import java.io.FileNotFoundException;
import java.util.UUID;

public class Fill {

    private Database db = new Database();
    int peopleAdded = 1;
    int eventsAdded = 0;

    public int getPeopleAdded() {
        return peopleAdded;
    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    //Populates the server's database with generated data for the specified user name.
    // The required "username" parameter must be a user already registered with the server.
    // If there is any data in the database already associated with the given user name, it is deleted.
    // The optional “generations” parameter lets the caller specify the number of generations of
    // ancestors to be generated, and must be a non-negative integer (the default is 4, which results in
    // 31 new persons each with associated events).

    /* @param username to connect to the user
     * @param generations just in case there is multiple generations
    * @return success boolean
    * this way it will have generations default as 4
     */

    public Result.FillResult fillDatabase(Request.FillRequest username, PersonModel user) {
        return fillDatabase(username, 4, user);
    }

    public Result.FillResult fillFromHandler(Request.FillRequest username) throws DataAccessException {
        try {
            db.openConnection();
            UserDao ud = new UserDao(db.getConnection());
            UserModel user = ud.findByUsername(username.getUsername());
            PersonDao pd = new PersonDao(db.getConnection());
            PersonModel person = pd.find(user.getPersonID());
            db.closeConnection(true);
            return fillDatabase(username, username.getGenerations(), person);
        } catch (DataAccessException e) {
            db.closeConnection(true);
        }
        return null;
    }

    public Result.FillResult fillDatabase(Request.FillRequest r, int generations, PersonModel user) {
        String message = null;
        Boolean success = false;
        if (generations < 1) {
            message = "Error: generations too small (negative number)";
            return new FillResult(message, success);
        }
        try {
            db.openConnection();
            PersonDao person = new PersonDao(db.getConnection());//generate data
            UserDao userUser = new UserDao(db.getConnection());
            UserModel u;
            u = userUser.findByPersonID(user.getPersonID());
            person.deleteFromUsername(user.getuser());
            user = person.personFromUser(u);
            fillHelp(generations, r.getUsername(), user.getPersonID(), user.getMotherID(), user.getFatherID(), 1970);
            db.closeConnection(true);
            success = true;
            message = "Successfully added " + peopleAdded + " persons and " + eventsAdded + " events to the database.";
        } catch (DataAccessException e) {
            message = "Error:" + e.getMessage();
            success = false;
            e.printStackTrace();
        }
        return new FillResult(message, success);
    }

    boolean fillHelp(int generationsLeft, String username, String personID, String motherID, String fatherID, int yearOfBirth) throws DataAccessException {
        //create female anscestors
        try {
            if (generationsLeft == 0) {
                return true;
            }
            PersonDao person = new PersonDao(db.getConnection());
            person.generatePeopleNames();
            String mothersfatherID = generatePersonID();
            String mothersmotherID = generatePersonID();
            String fathersfatherID = generatePersonID();
            String fathersmotherID = generatePersonID();
            PersonModel mama;
            PersonModel dad;
            if (generationsLeft == 1) {
                mama = new PersonModel(motherID, username, person.randomFemaleName(), person.randomSurName(), "f", null, null, fatherID);
                dad = new PersonModel(fatherID, username, person.randomMaleName(), person.randomSurName(), "m", null, null, motherID); //create spouse
            }
            else {
                mama = new PersonModel(motherID, username, person.randomFemaleName(), person.randomSurName(), "f", mothersfatherID, mothersmotherID, fatherID);
                dad = new PersonModel(fatherID, username, person.randomMaleName(), person.randomSurName(), "m", fathersfatherID, fathersmotherID, motherID); //create spouse
            }
            person.insert(mama);
            peopleAdded += 1;
            person.insert(dad);
            peopleAdded += 1;
            EventDao eventCreator = new EventDao(db.getConnection());
            String momBirthID = eventCreator.generateBirth(motherID, mama.getuser(), yearOfBirth);
            eventsAdded += 1;
            eventCreator.generateDeath(motherID, momBirthID, yearOfBirth + 69, mama.getuser());
            eventsAdded += 1;
            eventCreator.generateMarriage(motherID, fatherID, mama.getuser(), yearOfBirth + 20);
            eventsAdded += 2;
            String dadBirthID = eventCreator.generateBirth(dad.getPersonID(), dad.getuser(), yearOfBirth);
            eventsAdded += 1;
            eventCreator.generateDeath(fatherID, dadBirthID, yearOfBirth + 69, dad.getuser());
            eventsAdded += 1;
            fillHelp(generationsLeft - 1, username, motherID, mothersmotherID, mothersfatherID, yearOfBirth - 40);
            fillHelp(generationsLeft - 1, username, fatherID, fathersmotherID, fathersfatherID, yearOfBirth - 40);
        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String generatePersonID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
