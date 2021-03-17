package Request;

public class PersonRequest {

    String authToken;

    public PersonRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
