package Result;
import Model.PersonModel;

import java.util.ArrayList;

public class PersonResult {

    ArrayList<PersonModel> data;
    boolean success;
    String message;

    //"data": [  /* Array of Person objects */  ]
    //“success”:true		// Boolean identifier


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonResult(ArrayList<PersonModel> data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }
}
