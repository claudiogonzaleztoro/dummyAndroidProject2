package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetResponse {

    public String idPet;
    public String name;
    public int idColor;
    public int idSize;
    public int idBreed;
    public int idPetType;
    public int idPetGender;
    public int idContexture;
    
    public PetResponse(){

    }
}
