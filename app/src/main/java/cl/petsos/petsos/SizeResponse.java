package cl.petsos.petsos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SizeResponse {

    public String idSize;
    public String size;
    public SizeResponse(){

    }
}
