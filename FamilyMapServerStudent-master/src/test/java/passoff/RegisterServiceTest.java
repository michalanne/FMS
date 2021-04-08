package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Request.EventRequest;
import Result.UserLoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.UserRegisterRequest;
import Result.EventResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RegisterServiceTest{

    private PersonModel bestPerson;
    private Event es;
    private UserRegister rs;
    private Result.UserRegisterResult registerResult;
    private Request.UserRegisterRequest registerRequest;
    private Person ps;
    private UserLogin ls;
    private Result.UserLoginResult loginResult;
    private Result.PersonResult personResult;
    private Result.EventResult eventResult;
    private Clear cs = new Clear();


    @BeforeEach
    public void setUp() throws Exception {
        cs.clear();
        rs = new UserRegister();
    }


    @AfterEach
    public void tearDown() throws Exception {
        cs.clear();
    }



    @Test // tests empty response and bad auth
    public void registerTestSuccess() throws Exception {
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");

        registerResult=rs.register(registerRequest);
        assertNotNull(registerResult.getAuthtoken());
        assertNotNull(registerResult.getPersonID());
        assertNotNull(registerResult.getUsername());
        eventResult = new EventResult();
        es = new Event();
        EventRequest er = new EventRequest(registerResult.getAuthtoken());
        eventResult = es.allEvents(er);
        assertNotNull(eventResult.getData());
        assertEquals(eventResult.getData().size(),91 );


    }

    @Test // tests empty response and bad auth
    public void registerInvalid() throws Exception {
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m/f");

        registerResult=rs.register(registerRequest);
        UserRegisterResult duplicate = rs.register(registerRequest);

        assertNull(duplicate.getAuthtoken());
        assertNull(duplicate.getPersonID());
        assertNull(duplicate.getUsername());

        assertTrue(duplicate.getMessage().contains("error"));


    }

}

