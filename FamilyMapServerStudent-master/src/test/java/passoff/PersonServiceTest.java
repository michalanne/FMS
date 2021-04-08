package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Request.PersonPersonIDRequest;
import Request.PersonRequest;
import Result.PersonPersonIDResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.UserRegisterRequest;
import Result.EventResult;
import Result.PersonResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonServiceTest{

    private Person bestPerson;
    private Event es;
    private UserRegister rs;
    private Result.UserRegisterResult registerResult;
    private Request.UserRegisterRequest registerRequest;
    private Person ps;
    private PersonPersonID pID;
    private UserLogin ls;
    private PersonPersonIDRequest prID;
    private PersonPersonIDResult prIDresult;
    private Result.UserLoginResult loginResult;
    private Result.PersonResult personResult;
    private Result.EventResult eventResult;
    private Clear cs = new Clear();


    @BeforeEach
    public void setUp() throws DataAccessException {
        cs.clear();
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        rs = new UserRegister();
        registerResult=rs.register(registerRequest);
        personResult = null;
        ps = new Person();
        pID = new PersonPersonID();
    }


    @AfterEach
    public void tearDown()  {
        cs.clear();
    }



    @Test // tests empty response and bad auth
    public void personListValid() throws DataAccessException {
        PersonRequest pr = new PersonRequest(registerResult.getAuthtoken());
        personResult = ps.familyMembersOf(pr);
        assertNotNull(personResult.getData());
        assertEquals(personResult.getData().size(),31 );


    }

    @Test // tests empty response and bad auth
    public void personListInvalid() throws DataAccessException {
        PersonRequest pr = new PersonRequest("bad auth");

        personResult = ps.familyMembersOf(pr);
        assertNull(personResult.getData());
        assertTrue(personResult.getMessage().toLowerCase().contains("error"));

    }

    @Test // tests empty response and bad auth
    public void personSearchValid() {
        PersonPersonIDRequest pr = new PersonPersonIDRequest(registerResult.getPersonID());
        prIDresult = pID.findPerson(pr);
        assertNotNull(prIDresult.getPersonID());
        assertEquals(prIDresult.getFirstName(), registerRequest.getFirstName());
        assertEquals(prIDresult.getLastName(), registerRequest.getLastName());

    }

    @Test // tests empty response and bad auth
    public void personSearchInvalid() {
        PersonPersonIDRequest pr = new PersonPersonIDRequest("invalid ID");
        prIDresult = pID.findPerson(pr);
        assertNull(prIDresult.getPersonID());

        assertNull(prIDresult.getFirstName());
        assertTrue(prIDresult.getMessage().toLowerCase().contains("error"));

    }

}

