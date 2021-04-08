package Services;
import Dao.DataAccessException;
import Model.PersonModel;
import Model.EventModel;
import Request.LoadRequest;
import Result.LoadResult;
import Services.Clear;
import Dao.Database;
import Dao.UserDao;
import Dao.EventDao;
import Dao.PersonDao;

import java.io.IOException;

public class Load {

    //Clears all data from the database (just like the /clear API), and then loads the posted
    // user, person, and event data into the database.

    /** @param request to know which user we are entering
    * @return success boolean to know if it worked or not
     */
    public LoadResult loadFill(LoadRequest request) {
        Clear cl = new Clear();
        cl.clear();
        String message = "";
        boolean success = false;
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            //actually start loading stuff
            EventDao addEvents = new EventDao(db.getConnection());
            for (int i = 0; i < request.numberOfEvents(); ++i) {
                addEvents.insert(request.events.get(i));
            }
            PersonDao addPeople = new PersonDao(db.getConnection());
            for (int i = 0; i < request.numberOfPeople(); ++i) {
                addPeople.insert(request.persons.get(i));
            }
            UserDao addUsers = new UserDao(db.getConnection());
            for (int i = 0; i < request.numberOfUsers(); ++i) {
                addUsers.insert(request.users.get(i));
            }
            success = true;
            message = "Successfully added " + request.numberOfUsers() + " users, " + request.numberOfPeople() + " persons, and " + request.numberOfEvents() + " events to the database.";
            db.closeConnection(true);
        } catch (DataAccessException r) {
            try {
                success = false;
                message = "Error: failed to clear  " + r.getMessage(); //can be something generic rather than actual error message
                db.closeConnection(true);
            } catch(DataAccessException j) {
                j.printStackTrace();
            }
        }
        return new LoadResult(message, success);
    }

}
