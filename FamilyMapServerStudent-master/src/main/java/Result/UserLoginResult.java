package Result;

public class UserLoginResult {

//"authtoken": "cf7a368f",	// Non-empty auth token string
//        "username": "susan",		// Username passed in with request
//        "personID": "39f9fe46"	// Non-empty string containing the Person ID of the
////    user’s generated Person object
//        “success”:true		// Boolean identifier

    String username;
    String personID;
    public boolean success;
    String authtoken = null;
    String message;

    public void nullify(){
        personID = null;
        authtoken = null;
        message = "Error: Login Failed.";
    }

    public UserLoginResult(String username, String personID, boolean success, String authtoken, String message) {
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.authtoken = authtoken;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getMessage() {
        return message;
    }
}
