package cl.petsos.petsos.model;

import java.util.List;

/**
 * Created by root on 11-07-16.
 */
public class Region {
    private int regionId;
    private String regionName;
    private List<Comuna> comunas;


    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(List<Comuna> comunas) {
        this.comunas = comunas;
    }
}
