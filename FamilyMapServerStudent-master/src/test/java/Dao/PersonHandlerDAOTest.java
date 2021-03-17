package Dao;

import Model.PersonModel;
import Model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonHandlerDAOTest {
    private Database db;
    private PersonModel bestPerson;
    private PersonModel secondBestPerson;
    private PersonModel thirdBestPerson;
    private PersonDao eDao;


    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new person with random data
        bestPerson = new PersonModel("Biking_123A", "George", "Lawrence", "Smith",
                "M", "1456", "LELL77", "MMA180");
        secondBestPerson = new PersonModel("LovesDoggos", "Lisa", "Ethyl", "Smith",
                "F", "DOESNTloveDOGGOS", "caramelcorn", "KEYSTONE");
        thirdBestPerson = new PersonModel("ISADOGGO", "Merlin", "Apollo", "Smith",
                "M", "Apollo?", "LEIA", "ETHyL");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the PersonDAO so it can access the database
        eDao = new PersonDao(conn);
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
        eDao.insert(bestPerson);
        //So lets use a find method to get the person that we just put in back out
        PersonModel compareTest = eDao.find(bestPerson.getPersonID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(bestPerson.getPersonID(), compareTest.getPersonID());
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        eDao.insert(bestPerson);
        //but our sql table is set up so that "personID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestPerson));
    }

    @Test void retrievalPass() throws DataAccessException {
        eDao.insert(bestPerson);
        eDao.insert(secondBestPerson);
        eDao.insert(thirdBestPerson);
        PersonModel compareTest = eDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test void retrievalFail() throws DataAccessException {
        eDao.insert(bestPerson);
        PersonModel compareTest = eDao.find("this doesn't exist");
        assertNull(compareTest);
        assertNotEquals(compareTest, bestPerson); //clear the database and try to find someone
    }

    @Test void clearPass() throws DataAccessException {
        eDao.insert(bestPerson);
        eDao.insert(secondBestPerson);
        eDao.insert(thirdBestPerson);
        db.clearTables();
        PersonModel compareTest = eDao.find(bestPerson.getPersonID());
        PersonModel compareTest2 = eDao.find(secondBestPerson.getPersonID());
        PersonModel compareTest3 = eDao.find(thirdBestPerson.getPersonID());
        assertNull(compareTest);
        assertNull(compareTest2);
        assertNull(compareTest3);
        assertNotEquals(compareTest, bestPerson);
        assertNotEquals(compareTest2, secondBestPerson);
        assertNotEquals(compareTest3, thirdBestPerson);
    }

}
