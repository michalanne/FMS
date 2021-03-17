package Request;

public class FillRequest {

    String username;
    int generations;

    public FillRequest(String usernamei, int generationsi) {
        username = usernamei;
        generations = generationsi;
    }

    public FillRequest(String username) {
        this.username = username;
        generations = 4;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
