package Result;

public class UserLoginResult {

//"authtoken": "cf7a368f",	// Non-empty auth token string
//        "username": "susan",		// Username passed in with request
//        "personID": "39f9fe46"	// Non-empty string containing the Person ID of the
////    user’s generated Person object
//        “success”:true		// Boolean identifier

    String username;
    String personID;
    boolean success;
    String authtoken;

    public UserLoginResult(String username, String personID, boolean success, String authtoken) {
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.authtoken = authtoken;
    }
}
