package cl.petsos.petsos.utils;

import android.support.annotation.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import cl.petsos.petsos.BreedResponse;
import cl.petsos.petsos.ColorResponse;
import cl.petsos.petsos.Comuna;
import cl.petsos.petsos.ContextureResponse;
import cl.petsos.petsos.PetGenderResponse;
import cl.petsos.petsos.PetTypeResponse;
import cl.petsos.petsos.PrefUtils;
import cl.petsos.petsos.Region;
import cl.petsos.petsos.RelationshipResponse;
import cl.petsos.petsos.SizeResponse;
import cl.petsos.petsos.StatusPetResponse;
import cl.petsos.petsos.User;
import cl.petsos.petsos.Utils;

/**
 * Created by root on 11-07-16.
 */
public class PetSOSUtility {
    private static PetSOSUtility petUtility;

    private String SERVER_URL = "https://petsos.herokuapp.com";
    //private String SERVER_URL = "http://172.17.100.170:8080";
    private String PORT_URL   = "8080";
    private String PET_GENDER_URL = SERVER_URL +  "/petGender/list";
    private String RELATIONSHIP_URL = SERVER_URL + "/relationship/list";
    private String PET_TYPE_URL = SERVER_URL + "/petType/list";
    private String BREED_URL = SERVER_URL + "/breeds/list";
    private String COLOR_URL = SERVER_URL + "/colors/list";
    private String SIZE_URL = SERVER_URL + "/sizes/list";
    private String CONTEXTURE_URL = SERVER_URL + "/contextures/list";
    private String STATUS_URL = SERVER_URL + "/states/list";
    private String CREATE_USER_URL = SERVER_URL +  "/persons/create";

    public static final String SELECTION = "Seleccione"; //TODO get this dynamically at the beggining

    PetGenderResponse[] gendersResponse;
    BreedResponse[] breedResponse;
    RelationshipResponse[] relationResponse;
    PetTypeResponse[] typeResponse;
    ColorResponse[] colorResponse;
    SizeResponse[] sizesResponse;
    ContextureResponse[] buildsResponse;
    StatusPetResponse[] statusResponse;

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
        gendersResponse = fetchPetGender();
        genders.add(SELECTION);

        if(gendersResponse !=null && gendersResponse.length >0 ) {
            for (int i = 1; i <= gendersResponse.length; i++) {
                genders.add(gendersResponse[i - 1].petGender);
            }
        }
        return genders;
    }

    public int getIdPetGenderByPetGenderName(String name){
        int id = 0;

        HashMap<Integer, String> gendersPet = getGendersPetHashMap();

        Iterator it = gendersPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getGendersPetHashMap() {
        HashMap<Integer,String> genderMap = new HashMap<Integer,String>();

        genderMap.put(0,SELECTION);
        if(gendersResponse !=null && gendersResponse.length >0 ) {
            for (int i = 1; i <= gendersResponse.length; i++) {
                genderMap.put(gendersResponse[i - 1].idPetGender, gendersResponse[i - 1].petGender);
            }
        }

        return genderMap;
    }
    public PetGenderResponse[] fetchPetGender() {

        try {

            URL url = new URL(PET_GENDER_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchPetGender.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchPetGender.****");
            }

/*
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } */else {
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
        relationResponse = fetchRelationship();
        relationships.add(SELECTION);

        if(relationResponse !=null && relationResponse.length >0 ) {
            for (int i = 1; i <= relationResponse.length; i++) {
                relationships.add(relationResponse[i - 1].relationship);
            }
        }
        return relationships;
    }

    public int getIdPetRelationshipByPetRelationshipName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getRelationshipPetHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }


    @NonNull
    public HashMap<Integer, String> getRelationshipPetHashMap() {
        HashMap<Integer,String> relationMap = new HashMap<Integer,String>();

        relationMap.put(0,SELECTION);
        if(relationResponse !=null && relationResponse.length >0 ) {
            for (int i = 1; i <= relationResponse.length; i++) {
                relationMap.put(relationResponse[i - 1].idRelationship, relationResponse[i - 1].relationship);
            }
        }

        return relationMap;
    }

    public RelationshipResponse[] fetchRelationship() {

        try {

            URL url = new URL(RELATIONSHIP_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;

            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchRelationship.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchRelationship.****");
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
        typeResponse = fetchPetType();
        types.add(SELECTION);

        if(typeResponse !=null && typeResponse.length >0 ) {
            for (int i = 1; i <= typeResponse.length; i++) {
                types.add(typeResponse[i - 1].petType);
            }
        }
        return types;
    }

    public int getIdPetTypeByPetTypeName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetTypesHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetTypesHashMap() {
        HashMap<Integer,String> typeMap = new HashMap<Integer,String>();

        typeMap.put(0,SELECTION);
        if(typeResponse !=null && typeResponse.length >0 ) {
            for (int i = 1; i <= typeResponse.length; i++) {
                typeMap.put(typeResponse[i - 1].idPetType, typeResponse[i - 1].petType);
            }
        }

        return typeMap;
    }


    private PetTypeResponse[] fetchPetType() {

        try {

            URL url = new URL(PET_TYPE_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
//
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchPetType.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchPetType.****");
            }
            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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
        breedResponse  = fetchBreed();
        breeds.add(SELECTION);
        if(breedResponse !=null && breedResponse.length >0 ) {
            for (int i = 1; i <= breedResponse.length; i++) {
                breeds.add(breedResponse[i - 1].breed);
            }
        }
        return breeds;
    }

    public int getIdPetBreedByPetBreedName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetBreedsHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetBreedsHashMap() {
        HashMap<Integer,String> breedMap = new HashMap<Integer,String>();

        breedMap.put(0,SELECTION);
        if(breedResponse !=null && breedResponse.length >0 ) {
            for (int i = 1; i <= breedResponse.length; i++) {
                breedMap.put(breedResponse[i - 1].idBreed, breedResponse[i - 1].breed);
            }
        }

        return breedMap;
    }



    private BreedResponse[] fetchBreed() {

        try {

            URL url = new URL(BREED_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchBreed.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchBreed.****");
            }
            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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
        colorResponse =  fetchColor();
        colors.add(SELECTION);
        if(colorResponse !=null && colorResponse.length >0 ) {
            for (int i = 1; i <= colorResponse.length; i++) {
                colors.add(colorResponse[i - 1].nameColor);
            }
        }
        return colors;
    }

    public int getIdPetColorByPetColorName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetColorsHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetColorsHashMap() {
        HashMap<Integer,String> colorMap = new HashMap<Integer,String>();

        colorMap.put(0,SELECTION);
        if(colorResponse !=null && colorResponse.length >0 ) {
            for (int i = 1; i <= colorResponse.length; i++) {
                colorMap.put(colorResponse[i - 1].idColor, colorResponse[i - 1].nameColor);
            }
        }

        return colorMap;
    }


    private ColorResponse[] fetchColor() {

        try {

            URL url = new URL(COLOR_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchColor.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchColor.****");
            }
            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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
        sizesResponse = fetchSize();
        sizes.add(SELECTION);
        if(sizesResponse !=null && sizesResponse.length >0 ) {
            for (int i = 1; i <= sizesResponse.length; i++) {
                sizes.add(sizesResponse[i - 1].size);
            }
        }
        return sizes;
    }

    public int getIdPetSizeByPetSizeName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetSizesHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetSizesHashMap() {
        HashMap<Integer,String> sizesMap = new HashMap<Integer,String>();

        sizesMap.put(0,SELECTION);
        if(sizesResponse !=null && sizesResponse.length >0 ) {
            for (int i = 1; i <= sizesResponse.length; i++) {
                sizesMap.put(sizesResponse[i - 1].idSize, sizesResponse[i - 1].size);
            }
        }

        return sizesMap;
    }

    private SizeResponse[] fetchSize() {

        try {

            URL url = new URL(SIZE_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;

            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchSize.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchSize.****");
            }

            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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
        buildsResponse = fetchContexture();
        builds.add(SELECTION);
        if(buildsResponse !=null && buildsResponse.length >0 ) {
            for (int i = 1; i <= buildsResponse.length; i++) {
                builds.add(buildsResponse[i - 1].contexture);
            }
        }
        return builds;
    }

    public int getIdPetBuildByPetBuildName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetBuildsHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetBuildsHashMap() {
        HashMap<Integer,String> buildsMap = new HashMap<Integer,String>();

        buildsMap.put(0,SELECTION);
        if(buildsResponse !=null && buildsResponse.length >0 ) {
            for (int i = 1; i <= buildsResponse.length; i++) {
                buildsMap.put(buildsResponse[i - 1].idContexture, buildsResponse[i - 1].contexture);
            }
        }

        return buildsMap;
    }


    private ContextureResponse[] fetchContexture() {

        try {

            URL url = new URL(CONTEXTURE_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchContexture.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchContexture.****");
            }
            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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

    public List<String> getPetStatus() {
        List<String> status = new ArrayList<String>();
        statusResponse = fetchPetStatus();
        status.add(SELECTION);
        if(statusResponse !=null && statusResponse.length >0 ) {
            for (int i = 1; i <= statusResponse.length; i++) {
                status.add(statusResponse[i - 1].petState);
            }
        }
        return status;
    }

    public int getIdPetStatusByPetBuildName(String name){
        int id = 0;

        HashMap<Integer, String> relPet = getPetStatusHashMap();

        Iterator it = relPet.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();
            String pname = (String) e.getValue();
            if(name.equals(pname)){
                id = ((Integer)e.getKey()).intValue();
                break;
            }
        }
        return id;
    }

    @NonNull
    public HashMap<Integer, String> getPetStatusHashMap() {
        HashMap<Integer,String> statusMap = new HashMap<Integer,String>();

        statusMap.put(0,SELECTION);
        if(statusResponse !=null && statusResponse.length >0 ) {
            for (int i = 1; i <= statusResponse.length; i++) {
                statusMap.put(statusResponse[i - 1].idState, statusResponse[i - 1].petState);
            }
        }
        return statusMap;
    }

    public StatusPetResponse[] fetchPetStatus() {

        try {

            URL url = new URL(STATUS_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            //
            SSLContext sslcontext = SSLContext.getInstance("TLSv1");

            sslcontext.init(null,
                    null,
                    null);
            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                System.out.println("Using HttpURLConnection for fetchPetStatus.");
            } else if(urlConnection instanceof HttpsURLConnection){
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                System.out.println("*** Using HttpsURLConnection for fetchPetStatus.****");
            }
            /*if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }*/ else {
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


            StatusPetResponse[] statusArray = (StatusPetResponse[])Utils.fromJson(urlString,StatusPetResponse[].class);
            return statusArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public void preCreateUser(final User user2){

        Thread tcreateUser = new Thread(new Runnable() {
            @Override
            public void run() {
                PetSOSUtility petSOSUtility = new PetSOSUtility();
                petSOSUtility.createUser(user2);
            }
        });
        tcreateUser.start();
    }


    public Object createUser(User user) {

        try {

            /*
            URL url = new URL(CREATE_USER_URL);
            String urlParameters  = "idPerson=claudita98@gmail.com";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );

            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            */


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(CREATE_USER_URL);
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("idPerson", "12345"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }



            return null;




        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


     /*

           runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    petBuildSpinner.setAdapter(adapter);
                }
            });

            */

}
