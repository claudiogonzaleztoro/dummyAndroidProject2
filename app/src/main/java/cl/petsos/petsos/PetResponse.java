package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetResponse {

    public String idPet;
    public String name;
    public String color;
    public int idColor;
    public int idSize;
    public String size;
    public int idBreed;
    public String breed;
    public int idPetType;
    public String petType;
    public int idPetGender;
    public String petGender;
    public int idContexture;
    public String contexture;
    
    public PetResponse(){

    }
}
