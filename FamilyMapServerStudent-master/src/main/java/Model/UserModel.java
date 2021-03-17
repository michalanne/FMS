package Model;

import java.util.Objects;

public class UserModel {

    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender; //f or m
    String personID;

    public UserModel(String username, String password, String email, String firstName,
                     String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return username.equals(userModel.username) && password.equals(userModel.password) && email.equals(userModel.email) && firstName.equals(userModel.firstName) && lastName.equals(userModel.lastName) && gender.equals(userModel.gender) && personID.equals(userModel.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender, personID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
