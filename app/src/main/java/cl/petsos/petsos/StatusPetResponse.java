package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 22-07-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusPetResponse {
    public int idState;
    public String petState;
    public StatusPetResponse(){

    }
}
