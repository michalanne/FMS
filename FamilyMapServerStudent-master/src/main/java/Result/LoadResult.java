package Result;
import Model.UserModel;
import Model.PersonModel;
import Model.EventModel;
import Services.Event;

public class LoadResult {

    //“message”: “Successfully added X users, Y persons, and Z events to the database.”
    //“success”:true		// Boolean identifier

    String message;
    boolean success;

    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
