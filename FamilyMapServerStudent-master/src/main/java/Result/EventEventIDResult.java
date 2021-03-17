package Result;

public class EventEventIDResult {

    String associatedUsername;
    String eventID;
    String personID;
    Float latitude;
    Float longitude;
    String country;
    String city;
    String eventType;
    int year;
    boolean success;

    //"associatedUsername": "susan"  // Username of user account this event belongs to
    //// (non-empty string)
    //	"eventID": "251837d7",	// Event’s unique ID (non-empty string)
    //	"personID": "7255e93e",	// ID of the person this event belongs to (non-empty string)
    //	"latitude": 65.6833,		// Latitude of the event’s location (number)
    //	"longitude": -17.9,		// Longitude of the event’s location (number)
    //	"country": "Iceland",		// Name of country where event occurred (non-empty
    ////    string)
    //	"city": "Akureyri",		// Name of city where event occurred (non-empty string)
    //	"eventType": "birth",		// Type of event (“birth”, “baptism”, etc.) (non-empty string)
    //	"year": 1912,			// Year the event occurred (integer)
    //“success”:true		// Boolean identifier


    public EventEventIDResult(String associatedUsername, String eventID, String personID, Float latitude,
                              Float longitude, String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }
}
