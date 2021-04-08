package Request;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadRequest {

    public ArrayList<UserModel> users;
    public ArrayList<PersonModel> persons;
    public ArrayList<EventModel> events;

    //“users”: [  /* Array of User objects */ ],
    //	“persons”: [  /* Array of Person objects */  ],
    //	“events”: [  /* Array of Event objects */  ]


    public LoadRequest(ArrayList<UserModel> users, ArrayList<PersonModel> persons, ArrayList<EventModel> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<UserModel> getUsers() {
        return users;
    }

    public ArrayList<PersonModel> getPersons() {
        return persons;
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public int numberOfUsers() {
        return users.size();
    }

    public int numberOfPeople() {
        return persons.size();
    }

    public int numberOfEvents() {
        return events.size();
    }
}
