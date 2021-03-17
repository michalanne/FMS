package Services;

import Dao.*;
import Model.AuthTokenModel;
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
        String message;
        String authtoken = null;
        String personID = null;
        try {
            db.openConnection();
            Fill f = new Fill();
            personID = f.generatePersonID();
            username = r.getUsername();
            authtoken = f.generatePersonID();
            AuthTokenModel authModel = new AuthTokenModel(authtoken, username);
            AuthTokenDao authDao = new AuthTokenDao(db.getConnection());
            UserDao ud = new UserDao(db.getConnection());
            PersonDao pd = new PersonDao(db.getConnection());
            UserModel user = new UserModel(username, r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
            ud.insert(user);
            authDao.insert(authModel);
            FillRequest fr = new FillRequest(username);
            PersonModel newperp = new PersonModel(personID, r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender(), f.generatePersonID(), f.generatePersonID(), null);
            pd.insert(newperp);
            db.closeConnection(true);
            f.fillDatabase(fr, newperp);
            success = true;
            message = "Successful register.";
            //db.closeConnection(true);
        } catch (DataAccessException e) {
            success = false;
            message = "Error: register messed up   " + e.getMessage();
            db.closeConnection(true);
        }
        return new UserRegisterResult(authtoken, username, personID, success, message);
    }

}
