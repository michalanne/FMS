package Handlers;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Result.ClearResult;
import Services.Clear;


public class ClearHandler extends HandlerHelper implements HttpHandler {

    public ClearHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Clear cs = new Clear();
                ClearResult cr = cs.clear(); //has no request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writer(cr,exchange); //Writes an output stream
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) { //error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}