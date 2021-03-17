package Handlers;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Model.AuthTokenModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;


import java.net.HttpURLConnection;
import java.util.ArrayList;
import Services.PersonPersonID;
import Services.Person;
import Request.PersonRequest;
import Request.PersonPersonIDRequest;
import Result.PersonResult;
import Result.PersonPersonIDResult;

import java.io.IOException;

public class PersonHandler extends HandlerHelper implements HttpHandler {

    Database db = new Database();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String urlString = exchange.getRequestURI().toString();
                ArrayList<String> urlList = breakUpURL(urlString);
                if (urlList.size() == 1) {
                    String authtoken;
                    Headers theHeader = exchange.getRequestHeaders();
                    if (exchange.getRequestHeaders().containsKey("Authorization")) {
                        authtoken = theHeader.getFirst("Authorization");
                        PersonRequest pr = new PersonRequest(authtoken);
                        Person pep = new Person();
                        PersonResult result = pep.familyMembersOf(pr);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        writer(result,exchange); //Writes an output stream
                        exchange.getResponseBody().close();
                    }
                    //call person (returns all familymembers)
                    //Description: Returns ALL family members of the current user. The current user is determined from the provided auth token.
                    //how do i get the auth token...? Nthing is input at all...
                }
                else {
                    //call person/personID returns person specifically
                    if (urlList.size() != 2) {
                        throw new IOException("url not the right size");
                    }
                    String IDtoFind = urlList.get(1);
                    PersonPersonID pI = new PersonPersonID();
                    PersonPersonIDRequest pr = new PersonPersonIDRequest(IDtoFind);
                    PersonPersonIDResult IDresult = pI.findPerson(pr);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writer(IDresult,exchange); //Writes an output stream
                    exchange.getResponseBody().close();
                }
            }
            } catch (IOException | DataAccessException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
                exchange.getResponseBody().close();
                e.printStackTrace();
        }
    }

}
