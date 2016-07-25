package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoundLostSearchActivity extends AppCompatActivity {

    private String SERVER_URL = "http://172.17.100.170";
    private String PORT_URL   = "8080";

    private String PET_URL = SERVER_URL + ":" + PORT_URL + "/pets/list";
    private String PET_TYPE_URL = SERVER_URL + ":" + PORT_URL + "/petType/list";
    private String PET_GENDER_URL = SERVER_URL + ":" + PORT_URL + "/petGender/list";
    private String RELATIONSHIP_URL = SERVER_URL + ":" + PORT_URL + "/relationship/list";
    private String REGION_URL = SERVER_URL + ":" + PORT_URL + "/regions/list";
    private String COMUNA_URL = SERVER_URL + ":" + PORT_URL + "/comunas/list";
    private String COLOR_URL = SERVER_URL + ":" + PORT_URL + "/colors/list";
    private String SIZE_URL = SERVER_URL + ":" + PORT_URL + "/sizes/list";
    private String CONTEXTURE_URL = SERVER_URL + ":" + PORT_URL + "/contextures/list";
    private String BREED_URL = SERVER_URL + ":" + PORT_URL + "/breeds/list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.found_lost_search);

        /*LLenar petType*/
        Thread tPetType = new Thread(new Runnable() {
            @Override
            public void run() {
                fillPetTypeSpinner(fetchPetType());
            }
        });
        tPetType.start();

        /*LLenar petGender*/
        Thread tPetGender = new Thread(new Runnable() {
            @Override
            public void run() {
                fillPetGenderSpinner(fetchPetGender());
            }
        });
        tPetGender.start();


        /*LLenar relationship*/
        Thread tRelationship = new Thread(new Runnable() {
            @Override
            public void run() {
                fillRelationshipSpinner(fetchRelationship());
            }
        });
        tRelationship.start();


        /*LLenar region*/
        Thread tRegion = new Thread(new Runnable() {
            @Override
            public void run() {
                fillRegionSpinner(fetchRegion());
            }
        });
        tRegion.start();

        /*LLenar comuna*/
        Thread tComuna = new Thread(new Runnable() {
            @Override
            public void run() {
                fillComunaSpinner(fetchComuna());
            }
        });
        tComuna.start();

        /*LLenar color*/
        Thread tColor = new Thread(new Runnable() {
            @Override
            public void run() {
                fillColorSpinner(fetchColor());
            }
        });
        tColor.start();

        /*LLenar size*/
        Thread tSize = new Thread(new Runnable() {
            @Override
            public void run() {
                fillSizeSpinner(fetchSize());
            }
        });
        tSize.start();

        /*LLenar contexture*/
        Thread tContexture = new Thread(new Runnable() {
            @Override
            public void run() {
                fillContextureSpinner(fetchContexture());
            }
        });
        tContexture.start();

        /*LLenar breed*/
        Thread tBreed = new Thread(new Runnable() {
            @Override
            public void run() {
                fillBreedSpinner(fetchBreed());
            }
        });
        tBreed.start();

        /*Cargar ListView*/

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(fetchAtpTennisReport());
            }
        });
        t.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private PetGenderResponse[] fetchPetGender() {

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


            PetGenderResponse[] petGenderArray = (PetGenderResponse[])Utils.fromJson(urlString,PetGenderResponse[].class);
            return petGenderArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private RelationshipResponse[] fetchRelationship() {

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

    private RegionResponse[] fetchRegion() {

        try {

            URL url = new URL(REGION_URL);
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


            RegionResponse[] regionArray = (RegionResponse[])Utils.fromJson(urlString,RegionResponse[].class);
            return regionArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ComunaResponse[] fetchComuna() {

        try {

            URL url = new URL(COMUNA_URL);
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


            ComunaResponse[] comunaArray = (ComunaResponse[])Utils.fromJson(urlString,ComunaResponse[].class);
            return comunaArray;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.listPetsItem:
                Intent i = new Intent(FoundLostSearchActivity.this,FoundLostActivity.class);
                startActivity(i);
                return true;

            case R.id.profileItem:
                i = new Intent(FoundLostSearchActivity.this,RegisterActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void backToMainThreadWithResponse(final List<PetResponse> response){

    }

    private void fillPetTypeSpinner(final PetTypeResponse[] petTypeResponse){

        final Spinner petTypeSpinner = (Spinner)findViewById(R.id.petTypeSpinner);
        List<String> petType = new ArrayList<String>();

        for(int i = 0; i < petTypeResponse.length; i++){
            petType.add(petTypeResponse[i].petType);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petTypeSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillPetGenderSpinner(final PetGenderResponse[] petGenderResponse){

        final Spinner petGenderSpinner = (Spinner)findViewById(R.id.petGenderSpinner);
        List<String> petGender = new ArrayList<String>();

        for(int i = 0; i < petGenderResponse.length; i++){
            petGender.add(petGenderResponse[i].petGender);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petGender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petGenderSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillRelationshipSpinner(final RelationshipResponse[] relationshipResponse){

        final Spinner relationshipSpinner = (Spinner)findViewById(R.id.relationshipSpinner);
        List<String> relationship = new ArrayList<String>();

        for(int i = 0; i < relationshipResponse.length; i++){
            relationship.add(relationshipResponse[i].relationship);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, relationship);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relationshipSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillRegionSpinner(final RegionResponse[] regionResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.regionSpinner);
        List<String> region = new ArrayList<String>();

        for(int i = 0; i < regionResponse.length; i++){
            region.add(regionResponse[i].region);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, region);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillComunaSpinner(final ComunaResponse[] comunaResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.comunaSpinner);
        List<String> comuna = new ArrayList<String>();

        for(int i = 0; i < comunaResponse.length; i++){
            comuna.add(comunaResponse[i].comuna);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, comuna);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillColorSpinner(final ColorResponse[] colorResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.colorPetSpinner);
        List<String> color = new ArrayList<String>();

        for(int i = 0; i < colorResponse.length; i++){
            color.add(colorResponse[i].color);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillSizeSpinner(final SizeResponse[] sizeResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.sizePetSpinner);
        List<String> size = new ArrayList<String>();

        for(int i = 0; i < sizeResponse.length; i++){
            size.add(sizeResponse[i].size);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillContextureSpinner(final ContextureResponse[] contextureResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.contextureSpinner);
        List<String> contexture = new ArrayList<String>();

        for(int i = 0; i < contextureResponse.length; i++){
            contexture.add(contextureResponse[i].contexture);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contexture);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillBreedSpinner(final BreedResponse[] breedResponse){

        final Spinner regionSpinner = (Spinner)findViewById(R.id.breedPetSpinner);
        List<String> breed = new ArrayList<String>();

        for(int i = 0; i < breedResponse.length; i++){
            breed.add(breedResponse[i].breed);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, breed);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                regionSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private List<PetResponse> fetchAtpTennisReport() {
        try {

            URL url = new URL(PET_URL);
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

            PetResponse[] petArray = (PetResponse[])Utils.fromJson(urlString,PetResponse[].class);
            List<PetResponse> videoList = Arrays.asList(petArray);
            return videoList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
