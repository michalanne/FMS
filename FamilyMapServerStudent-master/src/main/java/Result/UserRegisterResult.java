package Result;

public class UserRegisterResult {

    String authtoken; //has to be exactly same spelling
    String username;
    String personID;
    boolean success;
    String message;

//	"authtoken": "cf7a368f",	// Non-empty auth token string
//	"username": "susan",		// Username passed in with request
//	"personID": "39f9fe46"		// Non-empty string containing the Person ID of the
//			//  user’s generated Person object
//“success”:true		// Boolean identifier

    public boolean getSuccess(){
        return success;
    }

    public void nullify(){
        personID = null;
        authtoken = null;

    }

    public UserRegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }
}
