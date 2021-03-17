package Result;
import Model.EventModel;

import java.util.ArrayList;

public class EventResult {

    ArrayList<EventModel> data;
    boolean success;

    //"data": [  /* Array of Event objects */  ]
    //“success”:true		// Boolean identifier


    public EventResult(ArrayList<EventModel> data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
