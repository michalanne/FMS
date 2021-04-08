package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.LoadRequest;
import Request.UserRegisterRequest;
import Result.EventResult;
import Result.LoadResult;
import Result.PersonResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoadServiceTest{

    private PersonModel bestPerson;
    private Event es;
    private UserRegister rs;
    private Result.UserRegisterResult registerResult;
    private Request.UserRegisterRequest registerRequest;
    private Person ps;
    private UserLogin ls;
    private LoadRequest loadRequest;
    private LoadResult loadResult;
    private Load loadService;
    private Result.UserLoginResult loginResult;
    private Result.PersonResult personResult;
    private Result.EventResult eventResult;
    private Clear cs = new Clear();
    private ArrayList<PersonModel> persons = new ArrayList<>();
    private ArrayList<EventModel> events = new ArrayList<>();
    private ArrayList<UserModel> users = new ArrayList<>();




    @BeforeEach
    public void setUp() {
        cs.clear();
        loadService = new Load();
        for (int i =0; i<10; ++i) {
            UserModel newUser = new UserModel("u"+i,"p"+i,1+"@gmail.com","first"+1,"last"+i,"m","person"+i);

            users.add(newUser);
        }
        for (int i =0; i<10; ++i) {
            EventModel newEvent = new EventModel("event"+i,"u"+i,"person"+i,i,i,"country","city","felt"+i,i);
            events.add(newEvent);
        }
        for (int i =0; i<10; ++i) {
            PersonModel newPerson = new PersonModel("person"+i,"u"+i,"first"+i,"last"+i,"m", null, null, null);
            persons.add(newPerson);
        }
    }


    @AfterEach
    public void tearDown()  {
        cs.clear();

    }



    @Test // tests empty response and bad auth
    public void loadValid() {
        loadRequest = new LoadRequest(users, persons, events);
        Load l = new Load();

        loadResult = l.loadFill(loadRequest);

        assertTrue(loadResult.isSuccess());
    }


}

