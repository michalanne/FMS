package Handlers;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import Services.UserLogin;
import Request.UserLoginRequest;
import Result.UserLoginResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class LoginHandler extends HandlerHelper implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                UserLoginRequest lr;
                // Display/log the request JSON data
                System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                lr = gson.fromJson(reqData, UserLoginRequest.class); //reqData was toBeRead
                UserLogin cs = new UserLogin();
                UserLoginResult result = cs.login(lr);
                if(result.success){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    result.nullify();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                writer(result, exchange); //Writes an output stream
                exchange.getResponseBody().close();
            }
        } catch (IOException e) { //error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0); //saves the error number (500)
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
