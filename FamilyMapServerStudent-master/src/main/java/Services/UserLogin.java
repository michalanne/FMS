package Services;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Model.AuthTokenModel;
import Request.UserLoginRequest;
import Result.UserLoginResult;
import java.util.UUID;
import Model.UserModel;
import Dao.UserDao;
import Dao.Database;

public class UserLogin {

    //Logs in the user and returns an auth token.

    /* @param username to know who it is? not sure
    * @return authToken to verify user
     */

//    "authtoken": "cf7a368f",	// Non-empty auth token string
//            "username": "susan",		// Username passed in with request
//            "personID": "39f9fe46"	// Non-empty string containing the Person ID of the
////    user’s generated Person object
//            “success”:true		// Boolean identifier

    public UserLoginResult login(UserLoginRequest r) {
        boolean success = false;
        Database db = new Database();
        String authtoken = null;
        String username = null;
        String personID = null;
        UserModel person = null;
        String message = null;
        try {
            db.openConnection();
            UserDao udao = new UserDao(db.getConnection());
            username = r.getUsername();
            person = udao.findByUsername(r.getUsername()); //double check that this gets the right thing bc I'm not sure
            if (person == null || !person.getPassword().equals(r.getPassword())) {
                success = false;
                personID = null;
                person = null;
                username = null;
                message = "Error: login invalid";
            }
            else {
                personID = person.getPersonID();
                success = true;
                message = null;
                authtoken = UUID.randomUUID().toString();
                AuthTokenDao adao = new AuthTokenDao(db.getConnection());
                AuthTokenModel auM = new AuthTokenModel(authtoken, r.getUsername());
                adao.insert(auM);
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            try {
                success = false;
                message = "Error: " + e.getMessage();
                db.closeConnection(true);
            } catch(DataAccessException d) {
                d.printStackTrace();
            }
        }
        return new UserLoginResult(username, personID, success, authtoken, message);
    }
}
