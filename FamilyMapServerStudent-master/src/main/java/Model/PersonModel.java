package Model;

import java.util.Objects;

public class PersonModel {

    String personID;
    String user;
    String firstName;
    String lastName;
    String gender;
    String fatherID; //possibly null
    String motherID;//possibly null
    String spouseID;//possibly null

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonModel that = (PersonModel) o;
        return personID.equals(that.personID) && user.equals(that.user) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && gender.equals(that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, user, firstName, lastName, gender, fatherID, motherID, spouseID);
    }

    public PersonModel(String personID, String user, String firstName, String lastName,
                       String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

}
