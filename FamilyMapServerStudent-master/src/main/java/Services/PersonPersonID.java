package Services;
import Dao.DataAccessException;
import Dao.Database;
import Model.PersonModel;
import Request.PersonPersonIDRequest;
import Result.PersonPersonIDResult;
import Dao.PersonDao;

public class PersonPersonID {

    public PersonPersonID() {
    }

    //Returns the single Person object with the specified ID.

    /* @param ID to find the person to return
    * @return PersonModel object
     */
    public PersonPersonIDResult findPerson(PersonPersonIDRequest ID) {
        Database db = new Database();
        String associatedUsername = null;
        String personID = null;
        String firstName = null;
        String lastName = null;
        String gender = null;
        String fatherID = null;
        String motherID = null;
        String spouseID = null;
        String message = null;
        boolean success = false;
        try {
            db.openConnection();
            PersonDao toReturn = new PersonDao(db.getConnection());
            PersonModel personInfo = toReturn.find(ID.getPersonID());
            if (personInfo == null) {
                message = "Error: personID not found";
                db.closeConnection(true);
            }
            else {
                associatedUsername = personInfo.getuser();
                personID = personInfo.getPersonID();
                firstName = personInfo.getFirstName();
                lastName = personInfo.getLastName();
                gender = personInfo.getGender();
                fatherID = personInfo.getFatherID();
                motherID = personInfo.getMotherID();
                spouseID = personInfo.getSpouseID();
                success = true;
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            success = false;
            message = "Error: " + e.getMessage();

            try {
                db.closeConnection(true);
            } catch(DataAccessException j) {
                j.printStackTrace();
            }
        }
        return new PersonPersonIDResult(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID, success, message);
    }
}
