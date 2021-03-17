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
    public EventResult allEvents(EventRequest r) {
        boolean success = false;
        EventDao event;
        ArrayList<EventModel> events = new ArrayList<>();
        try {
            Database db = new Database();
            db.openConnection();
            AuthTokenDao token = new AuthTokenDao(db.getConnection());
            AuthTokenModel tokenModel = token.find(r.getAuthToken());
            String user = tokenModel.getUser();
            event = new EventDao(db.getConnection());
            events = event.findAllWithUser(user);
            db.closeConnection(true);
            success = true;
        } catch (DataAccessException | SQLException e) {
            success = false;
            e.printStackTrace();
        }
        EventResult pr = new EventResult(events, success);
        return pr;
    }

}
