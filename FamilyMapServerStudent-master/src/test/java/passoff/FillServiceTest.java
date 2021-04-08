package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Request.UserRegisterRequest;
import Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.FillRequest;
import Request.UserRegisterRequest;
import Result.EventResult;
import Result.FillResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FillServiceTest{

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
    private Fill fs = new Fill();
    private FillRequest fillRequest;
    private FillResult fillResult;


    @BeforeEach
    public void setUp() throws Exception {
        cs.clear();
        rs = new UserRegister();
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        registerResult=rs.register(registerRequest);
    }


    @AfterEach
    public void tearDown() throws Exception {
        cs.clear();
    }



    @Test // tests empty response and bad auth
    public void defaultFill() throws Exception {
        fillRequest = new FillRequest(registerResult.getUsername());
        fillResult = fs.fillFromHandler(fillRequest);
        assertTrue(fillResult.isSuccess());


    }

    @Test // tests empty response and bad auth
    public void fillMoreGenerations() throws Exception {
        fillRequest = new FillRequest(registerResult.getUsername(),5);
        fillResult = fs.fillFromHandler(fillRequest);
        assertTrue(fillResult.isSuccess());

        assertEquals(fs.getPeopleAdded(), 63);


    }

    @Test // tests empty response and bad auth
    public void invalidGenerationNumber() throws Exception {
        fillRequest = new FillRequest(registerResult.getUsername(),-1);
        fillResult = fs.fillFromHandler(fillRequest);
        assertFalse(fillResult.isSuccess());
        assertTrue(fillResult.getMessage().toLowerCase().contains("error"));
    }

}