package cl.petsos.petsos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoundLostActivity extends AppCompatActivity {


    private String PET_URL = "http://10.0.2.2:8080/pets/list";
    private String PET_TYPE_URL = "http://10.0.2.2:8080/petType/list";
    private String PET_GENDER_URL = "http://10.0.2.2:8080/petGender/list";
    private String RELATIONSHIP_URL = "http://10.0.2.2:8080/relationship/list";
    private String REGION_URL = "http://10.0.2.2:8080/regions/list";
    //private String PET_URL = "http://127.0.0.1:8080/pets/list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.found_lost);

        //LLenar petType
        Thread tPetType = new Thread(new Runnable() {
            @Override
            public void run() {
                fillPetTypeSpinner(fetchPetType());
            }
        });
        tPetType.start();

        //LLenar petGender
        Thread tPetGender = new Thread(new Runnable() {
            @Override
            public void run() {
                fillPetGenderSpinner(fetchPetGender());
            }
        });
        tPetGender.start();


        //LLenar relationship
        Thread tRelationship = new Thread(new Runnable() {
            @Override
            public void run() {
                fillRelationshipSpinner(fetchRelationship());
            }
        });
        tRelationship.start();



        /*

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pet_type, android.R.layout.simple_spinner_item);
        Spinner petRelationshipSpinner = (Spinner)findViewById(R.id.petRelationshipSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_relationship, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petRelationshipSpinner.setAdapter(adapter);

        Spinner regionSpinner = (Spinner)findViewById(R.id.regionSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.region, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);

        Spinner comunaSpinner = (Spinner)findViewById(R.id.comunaSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.comuna, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comunaSpinner.setAdapter(adapter);

        Spinner colorPetSpinner = (Spinner)findViewById(R.id.colorPetSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_color, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorPetSpinner.setAdapter(adapter);

        Spinner sizePetSpinner = (Spinner)findViewById(R.id.sizePetSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizePetSpinner.setAdapter(adapter);

        Spinner buildPetSpinner = (Spinner)findViewById(R.id.buildPetSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_build, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildPetSpinner.setAdapter(adapter);

        Spinner breedPetSpinner = (Spinner)findViewById(R.id.breedPetSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.pet_breed, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedPetSpinner.setAdapter(adapter);
        */
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(fetchAtpTennisReport());
            }
        });
        t.start();

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void backToMainThreadWithResponse(final List<PetResponse> response){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateListView(response);
            }
        });

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

    private void fillPetGenderSpinner(final PetGenderResponse[] petTypeResponse){

        final Spinner petGenderSpinner = (Spinner)findViewById(R.id.petGenderSpinner);
        List<String> petType = new ArrayList<String>();

        for(int i = 0; i < petTypeResponse.length; i++){
            petType.add(petTypeResponse[i].petGender);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petGenderSpinner.setAdapter(dataAdapter);
            }
        });
    }

    private void fillRelationshipSpinner(final RelationshipResponse[] petTypeResponse){

        final Spinner relationshipSpinner = (Spinner)findViewById(R.id.relationshipSpinner);
        List<String> petType = new ArrayList<String>();

        for(int i = 0; i < petTypeResponse.length; i++){
            petType.add(petTypeResponse[i].relationship);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relationshipSpinner.setAdapter(dataAdapter);
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



            //return Utils.fromJson(urlString,PetItemResponse[].class);

            PetResponse[] petArray = (PetResponse[])Utils.fromJson(urlString,PetResponse[].class);
            List<PetResponse> videoList = Arrays.asList(petArray);
            return videoList;



//            JSONObject xmlJSONObj = XML.toJSONObject(urlString);
//            xmlJSONObj = xmlJSONObj.getJSONObject("rss").getJSONObject("channel");
//            return (PetResponse)Utils.fromJson(xmlJSONObj.toString(),PetResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateListView(List<PetResponse> response){
        // draw the items
        ListView listview = (ListView)findViewById(R.id.listView);
        MyListViewAdapter adapter = new MyListViewAdapter(this);

        listview.setAdapter(adapter);
        adapter.setData(response);
    }

    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }
    */
}
