package Result;

public class FillResult {

    String message;
    boolean success;

    //“message”: “Successfully added X persons and Y events to the database.”
    //“success”:true		// Boolean identifier


    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
