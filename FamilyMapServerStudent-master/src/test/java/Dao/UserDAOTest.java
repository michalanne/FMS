package Dao;

import Model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDAOTest {
    private Database db;
    private UserModel bestUser;
    private UserModel secondBestUser;
    private UserModel thirdBestUser;
    private UserDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new user with random data
        bestUser = new UserModel("ETHYL145", "password<3", "mg@email", "Ethyl", "Smith",
                "F", "VOGUEDOG");
        secondBestUser = new UserModel("MERLIN665", "thepass<3", "our@email.com", "Merlin", "Smith",
                "M", "AUSSIEDOGGO");
        thirdBestUser = new UserModel("APOLLO74", "word<3", "beautiful@email", "Apollo", "Smith",
                "M", "BEADYDOOOO");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the PersonDAO so it can access the database
        eDao = new UserDao(conn);
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
        eDao.insert(bestUser);
        //So lets use a find method to get the user that we just put in back out
        UserModel compareTest = eDao.findByPersonID(bestUser.getPersonID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestUser.getPersonID(), compareTest.getPersonID());
    }

    @Test void retrievalPass() throws DataAccessException {
        eDao.insert(bestUser);
        eDao.insert(secondBestUser);
        eDao.insert(thirdBestUser);
        UserModel compareTest = eDao.findByPersonID(bestUser.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test void retrievalFail() throws DataAccessException {
        eDao.insert(bestUser);
        UserModel compareTest = eDao.findByPersonID("this doesn't exist");
        assertNull(compareTest);
        assertNotEquals(compareTest, bestUser);
    }

    @Test void clearPass() throws DataAccessException {
        eDao.insert(bestUser);
        eDao.insert(secondBestUser);
        eDao.insert(thirdBestUser);
        db.clearTables();
        UserModel compareTest = eDao.findByPersonID(bestUser.getPersonID());
        UserModel compareTest2 = eDao.findByPersonID(secondBestUser.getPersonID());
        UserModel compareTest3 = eDao.findByPersonID(thirdBestUser.getPersonID());
        assertNull(compareTest);
        assertNull(compareTest2);
        assertNull(compareTest3);
        assertNotEquals(compareTest, bestUser);
        assertNotEquals(compareTest2, secondBestUser);
        assertNotEquals(compareTest3, thirdBestUser);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        eDao.insert(bestUser);
        //but our sql table is set up so that "personID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestUser));
    }
}
