package cl.petsos.petsos.utils;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cl.petsos.petsos.Comuna;
import cl.petsos.petsos.PetGenderResponse;
import cl.petsos.petsos.Region;
import cl.petsos.petsos.User;
import cl.petsos.petsos.Utils;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtility {
    private static PetSOSUtility petUtility;

    private String SERVER_URL = "http://172.17.100.170";
    private String PORT_URL   = "8080";
    private String PET_GENDER_URL = SERVER_URL + ":" + PORT_URL + "/petGender/list";

    public static final String SELECTION = "Seleccione"; //TODO get this dynamically at the beggining


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

    // Init User gender
    //returns female or male as facebook do to return the gender user
    public String getGenderUserMapper(String gender, String genderMapped) {
        List<String> gendersUser = getGendersUser();
        HashMap gendersMap = getGendersUserHashMap(gendersUser);
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

    @NonNull
    public List<String> getGendersUser() {
        List<String> genders = new ArrayList<String>();
        genders.add("Seleccione");
        genders.add("Femenino");
        genders.add("Masculino");
        return genders;
    }

    @NonNull
    public HashMap<String, String> getGendersUserHashMap(List<String> genders) {
        HashMap<String,String> genderMap = new HashMap<String,String>();
        genderMap.put("Selection", genders.get(0));
        genderMap.put("female", genders.get(1));
        genderMap.put("male", genders.get(2));
        return genderMap;
    }

    // End User gender

    //init Pet gender
    @NonNull
    public List<String> getGendersPet() {
        List<String> genders = new ArrayList<String>();
        PetGenderResponse[] gendersResponse = fetchPetGender();
        genders.add(SELECTION);

        for(int i = 1; i <= gendersResponse.length; i++){
            genders.add(gendersResponse[i-1].petGender);
        }

        return genders;
    }

    @NonNull
    public HashMap<String, String> getGendersPetHashMap(List<String> genders) {
        HashMap<String,String> genderMap = new HashMap<String,String>();
        PetGenderResponse[] gendersResponse = fetchPetGender();

        genderMap.put("0",SELECTION);
        for(int i = 1; i <= gendersResponse.length; i++){
            genderMap.put(gendersResponse[i-1].idPetGender,gendersResponse[i-1].petGender);
        }

        return genderMap;
    }

    public PetGenderResponse[] fetchPetGender() {

        try {

            URL url = new URL(PET_GENDER_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("Please enter an HTTP URL.");
                return null;
            }
            String urlString = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String current;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }


            PetGenderResponse[] petGenderArray = (PetGenderResponse[]) Utils.fromJson(urlString,PetGenderResponse[].class);
            return petGenderArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
