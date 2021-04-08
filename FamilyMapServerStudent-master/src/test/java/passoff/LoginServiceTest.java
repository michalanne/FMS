package passoff;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import Request.UserRegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.UserLoginRequest;
import Request.UserLoginRequest;
import Result.EventResult;
import Result.PersonResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginServiceTest{

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
    private Request.UserLoginRequest loginRequest;
    private Clear cs = new Clear();


    @BeforeEach
    public void setUp() throws DataAccessException {
        cs.clear();
        registerRequest = new UserRegisterRequest("username","password","coolguy@gmail.com","Elon","Musk","m");
        rs = new UserRegister();
        registerResult=rs.register(registerRequest);
        ls = new UserLogin();


    }


    @AfterEach
    public void tearDown()  {
        cs.clear();
    }



    @Test // tests empty response and bad auth
    public void loginSuccessful() {
        loginRequest = new UserLoginRequest("username","password");
       loginResult=ls.login(loginRequest);
       assertNotNull(loginResult.getAuthtoken());
       assertNotNull(loginResult.getPersonID());
       assertNotNull(loginResult.getUsername());
    }

    @Test // tests empty response and bad auth
    public void incorrectPassword() {
        loginRequest = new UserLoginRequest("username","theword");
        loginResult=ls.login(loginRequest);
        assertNull(loginResult.getAuthtoken());
        assertNull(loginResult.getPersonID());
        assertNull(loginResult.getUsername());
        assertTrue(loginResult.getMessage().toLowerCase().contains("error"));


    }

    @Test // tests empty response and bad auth
    public void incorrectUsername() {
        loginRequest = new UserLoginRequest("420Guy","password");
        loginResult=ls.login(loginRequest);
        assertNull(loginResult.getAuthtoken());
        assertNull(loginResult.getPersonID());
        assertNull(loginResult.getUsername());
        assertTrue(loginResult.getMessage().toLowerCase().contains("error"));


    }

}

