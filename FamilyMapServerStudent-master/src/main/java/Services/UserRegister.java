package Services;

import Dao.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.FillRequest;
import Request.UserLoginRequest;
import Request.UserRegisterRequest;
import Result.UserRegisterResult;


import java.io.FileNotFoundException;

public class UserRegister {

    Database db = new Database();

    //Creates a new user account, generates 4 generations of ancestor data for the new
    // user, logs the user in, and returns an auth token.

    /* @param username to store under username ?
    * @return authToken to verify user
     */

    //should be taking in as a parameter the request object
    //

    public UserRegisterResult register(UserRegisterRequest r) throws DataAccessException {
        boolean success;
        String username = null;
        String message = null;
        String authtoken = null;
        String personID = null;
        try {
            db.openConnection();
            Fill f = new Fill();
            username = r.getUsername();
            personID = f.generatePersonID();
            authtoken = f.generatePersonID();
            AuthTokenModel authModel = new AuthTokenModel(authtoken, username);
            AuthTokenDao authDao = new AuthTokenDao(db.getConnection());
            UserDao ud = new UserDao(db.getConnection());
            PersonDao pd = new PersonDao(db.getConnection());
            UserModel user = new UserModel(username, r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
            if (ud.findByUsername(username) != null) {
                success = false;
                username = null;
                message = "error: User already exists";
                authtoken = null;
                db.closeConnection(true);
                personID = null;
            }
            else {
                ud.insert(user);
                FillRequest fr = new FillRequest(username);
                PersonModel newperp = new PersonModel(personID, r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender(), f.generatePersonID(), f.generatePersonID(), null);
                pd.insert(newperp);
                EventDao edao = new EventDao(db.getConnection());
                edao.generateBirth(personID, username, 2000);
                authDao.insert(authModel);
                db.closeConnection(true);
                f.fillDatabase(fr, newperp);
                success = true;
                message = null;
            }
            //db.closeConnection(true);
        } catch (DataAccessException e) {
            success = false;
             username = null;
             authtoken = null;
             personID = null;
            message = "Error: register messed up   " + e.getMessage();
            db.closeConnection(true);
        }
        return new UserRegisterResult(authtoken, username, personID, success, message);
    }

}
