package Result;

public class PersonPersonIDResult {

    String associatedUsername;
    String personID;
    String firstName;
    String lastName;
    String gender;
    String fatherID;
    String motherID;
    String spouseID;
    boolean success;
    String message;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setFailure(){
        associatedUsername = null;
        personID = null;
        firstName = null;
        lastName = null;
        gender = null;
        fatherID = null;
        motherID = null;
        spouseID = null;
        success = false;
        message = "Error: not valid";
    }

    public PersonPersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    public PersonPersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.success = success;
    }

    public PersonPersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.success = success;
    }

    public PersonPersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.success = success;
    }



}
