package cl.petsos.petsos;


import java.util.ArrayList;
import java.util.List;

public class User {

    private int id_person;

    private String name;

    private String email;

    private String facebookID;

    private String gender;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    private List<UserComuna> userComunas = new ArrayList<UserComuna>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public List<UserComuna> getUserComunas() {
        return userComunas;
    }

    public void setUserComunas(List<UserComuna> userComunas) {
        this.userComunas = userComunas;
    }
}
