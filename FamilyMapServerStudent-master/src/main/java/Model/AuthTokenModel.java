package Model;

import java.util.Objects;

public class AuthTokenModel {

    String theAuthToken;
    String user;

    public AuthTokenModel(String theAuthToken, String user) {
        this.theAuthToken = theAuthToken;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenModel authToken = (AuthTokenModel) o;
        return theAuthToken.equals(authToken.theAuthToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theAuthToken);
    }

    public String getTheAuthToken() {
        return theAuthToken;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTheAuthToken(String theAuthToken) {
        this.theAuthToken = theAuthToken;
    }
}
