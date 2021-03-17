package Result;

public class ClearResult {

    String message;
    boolean success;

    //“message”: “Clear succeeded.”
    //“success”:true		// Boolean identifier


    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
