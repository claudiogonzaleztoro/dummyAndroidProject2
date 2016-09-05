package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ComunaResponse {

    private Integer comunaId;
    private String comunaName;
    public ComunaResponse(){

    }

    public Integer getComunaId() {
        return comunaId;
    }

    public void setComunaId(Integer comunaId) {
        this.comunaId = comunaId;
    }

    public String getComunaName() {
        return comunaName;
    }

    public void setComunaName(String comunaName) {
        this.comunaName = comunaName;
    }
}
