package Request;

public class EventEventIDRequest {

    String eventID;
    String authToken;

    public EventEventIDRequest(String eventID, String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
