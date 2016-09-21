package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by root on 25-08-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenderUser {
    private Integer genderuserId;
    private String genderName;

    public GenderUser() {
    }

    public Integer getGenderuserId() {
        return genderuserId;
    }

    public void setGenderuserId(Integer genderuserId) {
        this.genderuserId = genderuserId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}
