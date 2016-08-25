package cl.petsos.petsos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Integer userId;

    private String name;

    private String email;

    private String facebookID;

    private GenderUser gender;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    private Comuna comuna;

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

    public GenderUser getGender() {     return gender;    }

    public void setGender(GenderUser gender) {     this.gender = gender;   }

    public Integer getUserId() {       return userId;   }

    public void setUserId(Integer userId) {       this.userId = userId;   }

    public Comuna getComuna() {      return comuna;    }

    public void setComuna(Comuna comuna) {    this.comuna = comuna;   }
}
