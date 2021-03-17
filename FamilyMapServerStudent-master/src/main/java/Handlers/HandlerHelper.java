package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HandlerHelper {

    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
    public void writer(Object cr, HttpExchange ex) throws IOException {
        Gson gson = new Gson();
        OutputStreamWriter osq = new OutputStreamWriter(ex.getResponseBody());
        osq.write(gson.toJson(cr));
        osq.flush(); //seems unnecessary to me
    }

    public ArrayList<String> breakUpURL(String url) {
        ArrayList<String> forReturn = new ArrayList<String>();
        StringBuilder toAdd = new StringBuilder();
        for (int i = 0; i < url.length(); ++i) {
            if (url.charAt(i) == '/') {
                if (toAdd.length() != 0) {
                    forReturn.add(toAdd.toString());
                }
                toAdd = new StringBuilder(new String());
            }
            else {
                toAdd.append(url.charAt(i));
                if (i == url.length() - 1) {
                    forReturn.add(toAdd.toString());
                }
            }
        }
        return forReturn;
    }

}
