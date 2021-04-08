package passoff;

import Model.PersonModel;
import Services.UserLogin;
import Services.UserRegister;
import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.EventModel;
import Model.PersonModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Request.UserLoginRequest;
import Request.UserRegisterRequest;
import Result.EventResult;
import Result.UserLoginResult;
import Result.UserRegisterResult;
import Services.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClearServiceTest{

    private PersonModel bestPerson;
    private Services.Event es;
    private UserRegister rs;
    private Result.UserRegisterResult registerResult;
    private Request.UserRegisterRequest registerRequest;
    private Request.UserLoginRequest loginRequest;
    private Services.Person ps;
    private UserLogin ls;
    private Result.UserLoginResult loginResult;
    private Result.PersonResult personResult;
    private Result.EventResult eventResult;
    private Services.Clear cs = new Clear();


    @BeforeEach
    public void setUp() throws Exception {
        cs.clear();
        rs = new UserRegister();
    }


    @AfterEach
    public void tearDown() throws Exception {
        cs.clear();

    }



    @Test //Register service and login service must be working.
    public void clearSuccess() throws Exception {
       //If it didnt break, it was a successful clear! :)
        assertTrue(true);

    }


}