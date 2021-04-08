package Services;
import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDao;
import Model.AuthTokenModel;
import Model.EventModel;
import Request.EventEventIDRequest;
import Result.EventEventIDResult;

import javax.xml.crypto.Data;

public class EventEventID {

    // Returns the single Event object with the specified ID.
    /* @param ID so we know where to find it
    * @return Event
    * @author me
     */

    public EventEventIDResult specificEvent(EventEventIDRequest ID) throws DataAccessException {
        Database db = new Database();
        String eventID = null;
        String associatedUser = null;
        String personID = null;
        float latitude = -1;
        float longitude = -1;
        String country = null;
        String city = null;
        String eventType = null; //not totally sure if this should be a string.....
        int year = -1;
        boolean success = false;
        String message = null;
        try {
            db.openConnection();
            EventDao eventd = new EventDao(db.getConnection());
            EventModel eventInfo = eventd.find(ID.getEventID());
            AuthTokenDao audao = new AuthTokenDao(db.getConnection());
            AuthTokenModel auM = audao.find(ID.getAuthToken());
            associatedUser = eventInfo.getassociatedUsername();
            if (auM == null || !eventInfo.getassociatedUsername().equals(auM.getUser())) {
                db.closeConnection(true);
                message = "Error: event not associated with username";
                return new EventEventIDResult(null, eventID, personID, latitude, longitude, country, city, eventType, year, success, message);
            }
            personID = eventInfo.getPersonID();
            eventID = eventInfo.getEventID();
            latitude = eventInfo.getLatitude();
            longitude = eventInfo.getLongitude();
            country = eventInfo.getCountry();
            city = eventInfo.getCity();
            eventType = eventInfo.getEventType();
            year = eventInfo.getYear();
            success = true;
            db.closeConnection(true);
        } catch (DataAccessException e) {
            success = false;
            db.closeConnection(true);
            message = "Error: " + e.getMessage();
            try {
                db.closeConnection(true);
            } catch (DataAccessException j) {
                j.printStackTrace();
            }
        }
        return new EventEventIDResult(associatedUser, eventID, personID, latitude, longitude, country, city, eventType, year, success, message);
    }

}
