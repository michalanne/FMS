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
import Services.EventEventID;
import Services.Event;
import Request.EventRequest;
import Request.EventEventIDRequest;
import Result.EventResult;
import Result.EventEventIDResult;

import java.io.IOException;

public class EventHandler extends HandlerHelper implements HttpHandler {

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
                        EventRequest pr = new EventRequest(authtoken);
                        Event pep = new Event();
                        EventResult result = pep.allEvents(pr);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        writer(result,exchange); //Writes an output stream
                        exchange.getResponseBody().close();
                    }
                    //call Event (returns all familymembers)
                    //Description: Returns ALL family members of the current user. The current user is determined from the provided auth token.
                    //how do i get the auth token...? Nthing is input at all...
                }
                else {
                    //call Event/EventID returns Event specifically
                    if (urlList.size() != 2) {
                        throw new IOException("url not the right size");
                    }
                    String IDtoFind = urlList.get(1);
                    EventEventID pI = new EventEventID();
                    EventEventIDRequest pr = new EventEventIDRequest(IDtoFind);
                    EventEventIDResult IDresult = pI.specificEvent(pr);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writer(IDresult,exchange); //Writes an output stream
                    exchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
