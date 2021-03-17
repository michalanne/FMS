package Result;
import Model.PersonModel;

import java.util.ArrayList;

public class PersonResult {

    ArrayList<PersonModel> data;
    boolean success;

    //"data": [  /* Array of Person objects */  ]
    //“success”:true		// Boolean identifier


    public PersonResult(ArrayList<PersonModel> data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
