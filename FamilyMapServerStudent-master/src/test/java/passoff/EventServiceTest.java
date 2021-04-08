package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Request.EventEventIDRequest;
import Request.EventRequest;
import Result.EventEventIDResult;
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

public class EventServiceTest{

    private Person bestPerson;
    private Event es;
    private UserRegister rs;
    private Result.UserRegisterResult registerResult;
    private Request.UserRegisterRequest registerRequest;
    private EventEventID eID;
    private Person ps;
    private UserLogin ls;
    private Result.UserLoginResult loginResult;
    private Result.PersonResult personResult;
    private Result.EventResult eventResult;
    private Clear cs = new Clear();


    @BeforeEach
    public void setUp() throws DataAccessException {
        cs.clear();
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        rs = new UserRegister();
        registerResult = rs.register(registerRequest);
        eventResult = new EventResult();
        es = new Event();

    }


    @AfterEach
    public void tearDown() {
        cs.clear();
    }



    @Test
    public void eventListValid() throws DataAccessException {
        EventRequest er = new EventRequest(registerResult.getAuthtoken());
        eventResult = es.allEvents(er);
        assertNotNull(eventResult.getData());
        assertEquals(eventResult.getData().size(),91 );
        assertTrue(eventResult.isSuccess());

    }


    @Test
    public void eventListInvalid() throws DataAccessException {
        EventRequest er = new EventRequest("fake auth");
        eventResult = es.allEvents(er);
        assertNull(eventResult.getData());
        assertTrue(eventResult.getMessage().toLowerCase().contains("error"));
        assertFalse(eventResult.isSuccess());
    }

    @Test
    public void eventSearchValid() throws DataAccessException {

        EventRequest er = new EventRequest(registerResult.getAuthtoken());
        eventResult = es.allEvents(er);
        assertNotNull(eventResult.getData());
        EventModel ev = eventResult.getData().get(1);
        EventEventIDRequest ereq = new EventEventIDRequest(ev.getEventID(), registerResult.getAuthtoken());
        EventEventID eventID = new EventEventID();
        EventEventIDResult eresult = eventID.specificEvent(ereq);

        assertNotNull(eresult);
        assertEquals(eresult.getCity(),ev.getCity());
        assertTrue(eresult.isSuccess());

    }

}

