package Dao;

import Model.AuthTokenModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDaoTest {

    private Database db;
    private AuthTokenModel bestAuth;
    private AuthTokenModel secondbestAuth;
    private AuthTokenModel thirdbestAuth;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestAuth = new AuthTokenModel("10010010100", "George");
        secondbestAuth = new AuthTokenModel("500500500", "Lillly");
        thirdbestAuth = new AuthTokenModel("30342000", "Blaze it");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the AuthTokenDao so it can access the database
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error
        aDao.insert(bestAuth);
        //So lets use a find method to get the event that we just put in back out
        AuthTokenModel compareTest = aDao.find(bestAuth.getTheAuthToken());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestAuth, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        aDao.insert(bestAuth);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuth));
    }

    @Test void retrievalPass() throws DataAccessException {
        aDao.insert(bestAuth);
        aDao.insert(secondbestAuth);
        aDao.insert(thirdbestAuth);
        AuthTokenModel compareTest = aDao.find(bestAuth.getTheAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestAuth, compareTest);
    }

    @Test void retrievalFail() throws DataAccessException {
        aDao.insert(bestAuth);
        AuthTokenModel compareTest = aDao.find("this doesn't exist");
        assertNull(compareTest);
        assertNotEquals(compareTest, bestAuth); //clear the database and try to find someone
    }

    @Test void clearPass() throws DataAccessException {
        aDao.insert(bestAuth);
        aDao.insert(secondbestAuth);
        aDao.insert(thirdbestAuth);
        db.clearTables();
        AuthTokenModel compareTest = aDao.find(bestAuth.getTheAuthToken());
        AuthTokenModel compareTest2 = aDao.find(secondbestAuth.getTheAuthToken());
        AuthTokenModel compareTest3 = aDao.find(thirdbestAuth.getTheAuthToken());
        assertNull(compareTest);
        assertNull(compareTest2);
        assertNull(compareTest3);
        assertNotEquals(compareTest, bestAuth);
        assertNotEquals(compareTest2, secondbestAuth);
        assertNotEquals(compareTest3, thirdbestAuth);
    }
    
}
