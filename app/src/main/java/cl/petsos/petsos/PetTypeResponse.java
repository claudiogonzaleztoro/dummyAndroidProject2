package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetTypeResponse {

    public String idPetType;
    public String petType;
    public PetTypeResponse(){

    }
}
