package Request;

public class EventRequest {

    String authToken;

    public EventRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
