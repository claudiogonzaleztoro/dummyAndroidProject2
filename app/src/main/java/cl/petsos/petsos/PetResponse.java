package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetResponse {

    //public String status;
    //public List<PetItemResponse> item;
    //public String[] name;
    //public List<String> name;
    public String name;
    public PetResponse(){

    }
}
