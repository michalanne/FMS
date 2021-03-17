package Services;
import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.AuthTokenModel;
import Model.PersonModel;
import Request.PersonPersonIDRequest;
import Request.PersonRequest;
import Result.PersonResult;

import java.sql.SQLException;
import java.util.ArrayList;

public class Person {

    //Returns ALL family members of the current user. The current user is determined from the provided auth token.

    /* @param authToken to find which family member to return from
    * @return familyMembers as a []
     */
    public PersonResult familyMembersOf(PersonRequest authToken) throws DataAccessException {
        boolean success = false;
        PersonDao person;
        ArrayList<PersonModel> people = new ArrayList<>();
        try {
            Database db = new Database();
            db.openConnection();
            AuthTokenDao token = new AuthTokenDao(db.getConnection());
            AuthTokenModel tokenModel = token.find(authToken.getAuthToken());
            String user = tokenModel.getUser();
            person = new PersonDao(db.getConnection());
            people = person.findAllWithUser(user);
            db.closeConnection(true);
            success = true;
        } catch (DataAccessException | SQLException e) {
            success = false;
            e.printStackTrace();
        }
        PersonResult pr = new PersonResult(people, success);
        return pr;
    }

}
