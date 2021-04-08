package Result;
import Model.EventModel;

import java.util.ArrayList;

public class EventResult {

    ArrayList<EventModel> data;
    boolean success;
    String message;

    //"data": [  /* Array of Event objects */  ]
    //“success”:true		// Boolean identifier


    public ArrayList<EventModel> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public EventResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public EventResult(ArrayList<EventModel> data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

}
