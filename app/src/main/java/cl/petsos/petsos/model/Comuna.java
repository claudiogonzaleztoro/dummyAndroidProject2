package cl.petsos.petsos.model;

/**
 * Created by root on 11-07-16.
 */
public class Comuna {
    private int comunaId;
    private String comunaName;
    private int regionId;


    public Comuna(int comunaId, String comunaName) {
        this.comunaId = comunaId;
        this.comunaName = comunaName;
    }


    public String getComunaName() {
        return comunaName;
    }

    public void setComunaName(String comunaName) {
        this.comunaName = comunaName;
    }

    public int getComunaId() {
        return comunaId;
    }

    public void setComunaId(int comunaId) {
        this.comunaId = comunaId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
