package Handlers;

import Request.LoadRequest;
import Result.LoadResult;
import Services.Load;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.InputStream;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class LoadHandler extends HandlerHelper implements HttpHandler {

    public LoadHandler() { }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                LoadRequest lr;
                LoadResult lResult;

                // Display/log the request JSON data
                System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                lr = gson.fromJson(reqData, LoadRequest.class); //reqData was toBeRead
                Load cs = new Load();
                LoadResult cr = cs.loadFill(lr);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writer(cr, exchange); //Writes an output stream
                exchange.getResponseBody().close();
            }
        } catch (IOException e) { //error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}


