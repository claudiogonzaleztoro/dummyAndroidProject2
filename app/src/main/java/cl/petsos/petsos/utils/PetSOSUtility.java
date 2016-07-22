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

import cl.petsos.petsos.BreedResponse;
import cl.petsos.petsos.ColorResponse;
import cl.petsos.petsos.Comuna;
import cl.petsos.petsos.ContextureResponse;
import cl.petsos.petsos.PetGenderResponse;
import cl.petsos.petsos.PetTypeResponse;
import cl.petsos.petsos.Region;
import cl.petsos.petsos.RelationshipResponse;
import cl.petsos.petsos.SizeResponse;
import cl.petsos.petsos.User;
import cl.petsos.petsos.Utils;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtility {
    private static PetSOSUtility petUtility;

    private String SERVER_URL = "https://petsos.herokuapp.com";
    private String PORT_URL   = "8080";
    private String PET_GENDER_URL = SERVER_URL +  "/petGender/list";
    private String RELATIONSHIP_URL = SERVER_URL + "/relationship/list";
    private String PET_TYPE_URL = SERVER_URL + "/petType/list";
    private String BREED_URL = SERVER_URL + "/breeds/list";
    private String COLOR_URL = SERVER_URL + "/colors/list";
    private String SIZE_URL = SERVER_URL + "/sizes/list";
    private String CONTEXTURE_URL = SERVER_URL + "/contextures/list";

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

        if(gendersResponse !=null && gendersResponse.length >0 ) {
            for (int i = 1; i <= gendersResponse.length; i++) {
                genders.add(gendersResponse[i - 1].petGender);
            }
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

    ///init Pet Relationship
    @NonNull
    public List<String> getRelationshipPet() {
        List<String> relationships = new ArrayList<String>();
        RelationshipResponse[] relationshipResponse = fetchRelationship();
        relationships.add(SELECTION);

        if(relationshipResponse !=null && relationshipResponse.length >0 ) {
            for (int i = 1; i <= relationshipResponse.length; i++) {
                relationships.add(relationshipResponse[i - 1].relationship);
            }
        }
        return relationships;
    }

    @NonNull
    public HashMap<String, String> getRelationshipPetHashMap(List<String> relationships) {
        HashMap<String,String> relationMap = new HashMap<String,String>();
        RelationshipResponse[] relationResponse = fetchRelationship();

        relationMap.put("0",SELECTION);
        for(int i = 1; i <= relationResponse.length; i++){
            relationMap.put(relationResponse[i-1].idRelationship,relationResponse[i-1].relationship);
        }

        return relationMap;
    }

    public RelationshipResponse[] fetchRelationship() {

        try {

            URL url = new URL(RELATIONSHIP_URL);
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

            RelationshipResponse[] relationshipArray = (RelationshipResponse[])Utils.fromJson(urlString,RelationshipResponse[].class);
            return relationshipArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //init pet type
    public List<String> getPetTypes() {
        List<String> types = new ArrayList<String>();
        PetTypeResponse[] typeResponse = fetchPetType();
        types.add(SELECTION);

        if(typeResponse !=null && typeResponse.length >0 ) {
            for (int i = 1; i <= typeResponse.length; i++) {
                types.add(typeResponse[i - 1].petType);
            }
        }
        return types;
    }

    @NonNull
    public HashMap<String, String> getPetTypesHashMap(List<String> relationships) {
        HashMap<String,String> typeMap = new HashMap<String,String>();
        PetTypeResponse[] typeResponse = fetchPetType();

        typeMap.put("0",SELECTION);
        for(int i = 1; i <= typeResponse.length; i++){
            typeMap.put(typeResponse[i-1].idPetType,typeResponse[i-1].petType);
        }

        return typeMap;
    }


    private PetTypeResponse[] fetchPetType() {

        try {

            URL url = new URL(PET_TYPE_URL);
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


            PetTypeResponse[] petTypeArray = (PetTypeResponse[])Utils.fromJson(urlString,PetTypeResponse[].class);
            return petTypeArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //breeds

    public List<String> getPetBreeds() {
        List<String> breeds = new ArrayList<String>();
        BreedResponse[] breedResponse  = fetchBreed();
        breeds.add(SELECTION);
        if(breedResponse !=null && breedResponse.length >0 ) {
            for (int i = 1; i <= breedResponse.length; i++) {
                breeds.add(breedResponse[i - 1].breed);
            }
        }
        return breeds;
    }

    @NonNull
    public HashMap<String, String> getPetBreedsHashMap(List<String> breeds) {
        HashMap<String,String> breedMap = new HashMap<String,String>();
        BreedResponse[] breedResponse = fetchBreed();

        breedMap.put("0",SELECTION);
        for(int i = 1; i <= breedResponse.length; i++){
            breedMap.put(breedResponse[i-1].idBreed,breedResponse[i-1].breed);
        }

        return breedMap;
    }



    private BreedResponse[] fetchBreed() {

        try {

            URL url = new URL(BREED_URL);
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


            BreedResponse[] breedArray = (BreedResponse[])Utils.fromJson(urlString,BreedResponse[].class);
            return breedArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //color
    public List<String> getPetColors() {
        List<String> colors = new ArrayList<String>();
        ColorResponse[] colorResponse =  fetchColor();
        colors.add(SELECTION);
        if(colorResponse !=null && colorResponse.length >0 ) {
            for (int i = 1; i <= colorResponse.length; i++) {
                colors.add(colorResponse[i - 1].color);
            }
        }
        return colors;
    }

    @NonNull
    public HashMap<String, String> getPetColorsHashMap(List<String> colors) {
        HashMap<String,String> colorMap = new HashMap<String,String>();
        ColorResponse[] colorResponse = fetchColor();

        colorMap.put("0",SELECTION);
        for(int i = 1; i <= colorResponse.length; i++){
            colorMap.put(colorResponse[i-1].idColor,colorResponse[i-1].color);
        }

        return colorMap;
    }


    private ColorResponse[] fetchColor() {

        try {

            URL url = new URL(COLOR_URL);
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


            ColorResponse[] colorArray = (ColorResponse[])Utils.fromJson(urlString,ColorResponse[].class);
            return colorArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Size

    public List<String> getPetSizes() {
        List<String> sizes = new ArrayList<String>();
        SizeResponse[] sizesResponse = fetchSize();
        sizes.add(SELECTION);
        if(sizesResponse !=null && sizesResponse.length >0 ) {
            for (int i = 1; i <= sizesResponse.length; i++) {
                sizes.add(sizesResponse[i - 1].size);
            }
        }
        return sizes;
    }

    @NonNull
    public HashMap<String, String> getPetSizesHashMap(List<String> colors) {
        HashMap<String,String> sizesMap = new HashMap<String,String>();
        SizeResponse[] sizesResponse = fetchSize();

        sizesMap.put("0",SELECTION);
        for(int i = 1; i <= sizesResponse.length; i++){
            sizesMap.put(sizesResponse[i-1].idSize,sizesResponse[i-1].size);
        }

        return sizesMap;
    }

    private SizeResponse[] fetchSize() {

        try {

            URL url = new URL(SIZE_URL);
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


            SizeResponse[] sizeArray = (SizeResponse[])Utils.fromJson(urlString,SizeResponse[].class);
            return sizeArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //build
    public List<String> getPetBuilds() {
        List<String> builds = new ArrayList<String>();
        ContextureResponse[] buildsResponse = fetchContexture();
        builds.add(SELECTION);
        if(buildsResponse !=null && buildsResponse.length >0 ) {
            for (int i = 1; i <= buildsResponse.length; i++) {
                builds.add(buildsResponse[i - 1].contexture);
            }
        }
        return builds;
    }

    @NonNull
    public HashMap<String, String> getPetBuildsHashMap(List<String> colors) {
        HashMap<String,String> buildsMap = new HashMap<String,String>();
        ContextureResponse[] buildsResponse = fetchContexture();

        buildsMap.put("0",SELECTION);
        for(int i = 1; i <= buildsResponse.length; i++){
            buildsMap.put(buildsResponse[i-1].idContexture,buildsResponse[i-1].contexture);
        }

        return buildsMap;
    }


    private ContextureResponse[] fetchContexture() {

        try {

            URL url = new URL(CONTEXTURE_URL);
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


            ContextureResponse[] contextureArray = (ContextureResponse[])Utils.fromJson(urlString,ContextureResponse[].class);
            return contextureArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //status
    public String[] getPetStatus() {
             String[] status= {"Seleccione"};
        /*
        try {

            URL url = new URL(CONTEXTURE_URL);
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


            ContextureResponse[] contextureArray = (ContextureResponse[])Utils.fromJson(urlString,ContextureResponse[].class);
            return contextureArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/

            return status;
    }

}
