package cl.petsos.petsos.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cl.petsos.petsos.Comuna;
import cl.petsos.petsos.Region;
import cl.petsos.petsos.User;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtility {
    private static PetSOSUtility petUtility;

    public  static PetSOSUtility getPetSOSUtility() {
        if (petUtility==null) {
            petUtility=new PetSOSUtility();
        }
        return petUtility;
    }

    public static boolean isUserRegisterComplete(User user) {
        if(user.getName() != null && !user.getName().trim().equals("")
                && user.getEmail() != null && !user.getEmail().trim().equals("")
                && user.getGender() != null && !user.getGender().trim().equals("")
                && user.getPassword() != null && !user.getPassword().trim().equals("")){
            return true;
        }
        return false;
    }


      /*  comunas.add("Arica");
        comunas.add("General Lagos");

        regionMap.put("Arica y Parinacota",comunas);

        comunas = new ArrayList<String>();
        comunas.add("Seleccione");
        comunas.add("Providencia");
        comunas.add("Santiago");
        comunas.add("Las Condes");
        regionMap.put("Santiago",comunas);*/


    //TODO: populate the data from DB

    public HashMap<Integer, Comuna> getComunas(){

        HashMap<Integer, Comuna> mComunas = new HashMap<Integer, Comuna>();
        Comuna mComuna = new Comuna();
        mComuna.setIdRegion(15);
        mComuna.setComuna("Arica");
        mComuna.setIdComuna(1);

        mComunas.put(mComuna.getIdComuna(),mComuna);

        mComuna = new Comuna();
        mComuna.setIdRegion(15);
        mComuna.setComuna("General Lagos");
        mComuna.setIdComuna(2);

        mComunas.put(mComuna.getIdComuna(),mComuna);

        mComuna = new Comuna();
        mComuna.setIdRegion(13);
        mComuna.setComuna("Providencia");
        mComuna.setIdComuna(1);

        mComunas.put(mComuna.getIdComuna(),mComuna);

        mComuna = new Comuna();
        mComuna.setIdRegion(13);
        mComuna.setComuna("Las Condes");
        mComuna.setIdComuna(2);

        mComunas.put(mComuna.getIdComuna(),mComuna);

        mComuna = new Comuna();
        mComuna.setIdRegion(13);
        mComuna.setComuna("Ñuñoa");
        mComuna.setIdComuna(3);

        mComunas.put(mComuna.getIdComuna(),mComuna);

        return mComunas;
    }

    //TODO: populate the data from DB
    public HashMap<Integer,Region> getRegiones(){
        HashMap<Integer, Region> regs = new HashMap<Integer, Region>();

        Region reg = new Region();
        reg.setIdCountry(56);
        reg.setIdRegion(15);
        reg.setRegion("Arica y Parinacota");

        regs.put(reg.getIdRegion(),reg);

        reg = new Region();
        reg.setIdCountry(56);
        reg.setIdRegion(13);
        reg.setRegion("Santiago");

        regs.put(reg.getIdRegion(),reg);

        return regs;
    }

    public int getIdComunaByComunaName(String comunaName){
        int idComuna = 0;
        HashMap<Integer,Comuna> regs = getComunas();

        Iterator it = regs.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            Comuna mcomuna = (Comuna) e.getValue();
            if(comunaName.equals(mcomuna.getComuna() )){
                idComuna = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return idComuna;
    }

    public int getIdRegionByRegioName(String regName){
        int idReg = 0;

        HashMap<Integer,Region> regs = getRegiones();

        Iterator it = regs.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            Region reg = (Region) e.getValue();
            if(regName.equals(reg.getRegion())){
                idReg = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return idReg;
    }

    public HashMap getGenderMap(){
        List<String> genders = new ArrayList<String>();
        genders.add("Seleccione");
        genders.add("Femenino");
        genders.add("Masculino");

        HashMap<String,String> genderMap = new HashMap<String,String>();
        genderMap.put("Selection", genders.get(0));
        genderMap.put("female", genders.get(1));
        genderMap.put("male", genders.get(2));
        return genderMap;
    }

    //returns female or male as facebook do to return the gender user
    public String getGenderMapper(String gender, String genderMapped) {
        HashMap gendersMap = PetSOSUtility.getPetSOSUtility().getGenderMap();
        Iterator it = gendersMap.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();

            if(gender.equals(e.getValue())){
                genderMapped = e.getKey().toString();
                break;
            }
        }
        return genderMapped;
    }


   /* private HashMap getComunasByRegion(String regionName){
        HashMap<Integer,List<String>> mregionMap = new HashMap<Integer,List<String>>();

        List<String> comunas = new ArrayList<String>();
        comunas.add("Seleccione");
        regionMap.put("Seleccione",comunas);

        comunas = new ArrayList<String>();
        comunas.add("Seleccione");
        comunas.add("Arica");
        comunas.add("General Lagos");

        regionMap.put("Arica y Parinacota",comunas);

        comunas = new ArrayList<String>();
        comunas.add("Seleccione");
        comunas.add("Providencia");
        comunas.add("Santiago");
        comunas.add("Las Condes");
        regionMap.put("Santiago",comunas);

        List<String> regionList = new ArrayList<String>();
        for(String regionName: regionMap.keySet()){
            regionList.add(regionName);
        }
    }*/

}
