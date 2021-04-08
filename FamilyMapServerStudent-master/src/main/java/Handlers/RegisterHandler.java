package Handlers;

import Dao.DataAccessException;
import Request.UserRegisterRequest;
import Result.UserRegisterResult;
import Services.UserRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends HandlerHelper implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                UserRegisterRequest rr;
                // Display/log the request JSON data
                //System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                rr = gson.fromJson(reqData, UserRegisterRequest.class); //reqData was toBeRead
                UserRegister cs = new UserRegister();
                UserRegisterResult result = cs.register(rr);
                if(result.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    result.nullify();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                writer(result, exchange); //Writes an output stream
                exchange.getResponseBody().close();
            }
        } catch (IOException | DataAccessException e) { //error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
