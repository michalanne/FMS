package Handlers;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import Dao.DataAccessException;
import Request.FillRequest;
import Result.FillResult;
import Services.Fill;
import com.google.gson.Gson;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Result.ClearResult;
import Services.Clear;


public class FillHandler extends HandlerHelper implements HttpHandler {

    public FillHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String urlString = exchange.getRequestURI().toString();
                ArrayList<String> urlList;
                urlList = breakUpURL(urlString);
                if (urlList.size() < 1) {
                    throw new IOException("URL too small");
                }
                else if (urlList.size() > 3) {
                    throw new IOException("URL too big");
                }
                FillRequest fr = null;
                Fill cs = new Fill();
                if (urlList.size() > 2) {
                    int generations = Integer.parseInt(urlList.get(2));
                     fr = new FillRequest(urlList.get(1), generations);
                }
                else {
                    fr = new FillRequest(urlList.get(1));
                }
                FillResult cr = cs.fillFromHandler(fr);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writer(cr,exchange); //Writes an output stream
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) { //error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}