package Request;

public class UserRegisterRequest {

    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;

    //"username": "susan",		// Non-empty string
    //	"password": "mysecret",	// Non-empty string
    //	"email": "susan@gmail.com",	// Non-empty string
    //	"firstName": "Susan",		// Non-empty string
    //	"lastName": "Ellis",		// Non-empty string
    // "gender": "f"			// “f” or “m”


    public UserRegisterRequest(String usernamei, String passwordi, String emaili, String firstNamei, String lastNamei, String genderi) {
        username = usernamei;
        password = passwordi;
        email = emaili;
        firstName = firstNamei;
        lastName = lastNamei;
        gender = genderi;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
