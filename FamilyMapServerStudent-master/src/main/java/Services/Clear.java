package Services;

import Dao.DataAccessException;
import Dao.Database;
import Result.ClearResult;

public class Clear {


    /* @param nothing, I will call it on the model I want to clear
    *  @return success boolean
    *  @author me
    */
    public ClearResult clear() {
        boolean success = false;
        String message = "";
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            success = true;
            message = "Clear succeeded."; //from documentation
        } catch (DataAccessException e) {
            try {
                success = false;
                message = "Error: failed to clear  " + e.getMessage(); //can be something generic rather than actual error message
                db.closeConnection(true);
            } catch(DataAccessException r) {
                r.printStackTrace();
            }
        }
        ClearResult cr = new ClearResult(message, success);
        return cr;
    }

}
