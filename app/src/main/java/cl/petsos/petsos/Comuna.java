package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by root on 18-07-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comuna {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Integer comunaId;
    private String comunaName;
    private Region region;

    public Integer getComunaId() {
        return comunaId;
    }

    public void setComunaId(Integer comunaId) {
        this.comunaId = comunaId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getComunaName() {  return comunaName;   }

    public void setComunaName(String comunaName) {   this.comunaName = comunaName;   }

}
