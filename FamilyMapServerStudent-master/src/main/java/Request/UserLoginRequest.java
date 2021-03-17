package Request;

public class UserLoginRequest {

    String username;
    String password;

    //"username": "susan",		// Non-empty string
    //	"password": "mysecret"	// Non-empty string


    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
