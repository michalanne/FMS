package Services;
import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDao;
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

    //       Database db = new Database();
    //        String associatedUsername;
    //        String personID;
    //        String firstName;
    //        String lastName;
    //        String gender;
    //        String fatherID;
    //        String motherID;
    //        String spouseID;
    //        boolean success;
    //        try {
    //            db.openConnection();
    //            PersonDao toReturn = new PersonDao(db.getConnection());
    //            PersonModel personInfo = toReturn.find(ID.getPersonID());
    //            associatedUsername = personInfo.getAssociatedUser();
    //            personID = personInfo.getPersonID();
    //            firstName = personInfo.getFirstName();
    //            lastName = personInfo.getLastName();
    //            gender = personInfo.getGender();
    //            fatherID = personInfo.getFatherID();
    //            motherID = personInfo.getMotherID();
    //            spouseID = personInfo.getSpouseID();
    //            success = true;
    //            db.closeConnection(true);
    //            return new PersonPersonIDResult(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID, success);
    //        } catch (DataAccessException e) {
    //            try {
    //                db.closeConnection(true);
    //            } catch(DataAccessException j) {
    //                j.printStackTrace();
    //            }
    //        }
    //        return null;

    public EventEventIDResult specificEvent(EventEventIDRequest ID) {
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
        String username = null;
        boolean success = false;
        try {
            db.openConnection();
            EventDao eventd = new EventDao(db.getConnection());
            EventModel eventInfo = eventd.find(ID.getEventID());
            associatedUser = eventInfo.getAssociatedUser();
            personID = eventInfo.getPersonID();
            eventID = eventInfo.getEventID();
            latitude = eventInfo.getLatitude();
            longitude = eventInfo.getLongitude();
            country = eventInfo.getCountry();
            city = eventInfo.getCity();
            eventType = eventInfo.getEventType();
            year = eventInfo.getYear();
            success = true;
        } catch (DataAccessException e) {
            success = false;
            try {
                db.closeConnection(true);
            } catch (DataAccessException j) {
                j.printStackTrace();
            }
        }
        return new EventEventIDResult(associatedUser, eventID, personID, latitude, longitude, country, city, eventType, year, success);
    }

}
