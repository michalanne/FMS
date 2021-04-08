package Services;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Dao.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Request.EventRequest;
import Result.EventResult;
import Result.PersonResult;

public class Event {

    /* @param nothing
    * @return array of all events created
    * @author michal
    */
    public EventResult allEvents(EventRequest r) throws DataAccessException {
        boolean success = false;
        EventDao event;
        ArrayList<EventModel> events = null;
        Database db = new Database();
        String message = null;
        try {
            db.openConnection();
            AuthTokenDao token = new AuthTokenDao(db.getConnection());
            AuthTokenModel tokenModel = token.find(r.getAuthToken());
            if (tokenModel == null) {
                success = false;
                db.closeConnection(true);
                events = null;
                message = "Error: authToken not correct";
            }
            else {
                String user = tokenModel.getUser();
                event = new EventDao(db.getConnection());
                events = event.findAllWithUser(user);
                db.closeConnection(true);
                success = true;
            }
        } catch (DataAccessException | SQLException e) {
            success = false;
            events = null;
            try {
                db.closeConnection(true);
            } catch (DataAccessException j ) {
                j.getMessage();
                j.printStackTrace();
            }
            e.printStackTrace();
            message = "Error: " + e.getMessage();
        }
        EventResult pr = new EventResult(events, success, message);
        return pr;
    }

}
