package Request;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

public class LoadRequest {

    public UserModel[] users;
    public PersonModel[] persons;
    public EventModel[] events;

    //“users”: [  /* Array of User objects */ ],
    //	“persons”: [  /* Array of Person objects */  ],
    //	“events”: [  /* Array of Event objects */  ]


    public LoadRequest(UserModel[] users, PersonModel[] persons, EventModel[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public UserModel[] getUsers() {
        return users;
    }

    public PersonModel[] getPersons() {
        return persons;
    }

    public EventModel[] getEvents() {
        return events;
    }

    public int numberOfUsers() {
        return users.length;
    }

    public int numberOfPeople() {
        return persons.length;
    }

    public int numberOfEvents() {
        return events.length;
    }
}
