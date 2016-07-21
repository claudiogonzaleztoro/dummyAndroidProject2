package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoundLostActivity extends AppCompatActivity {


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


    //  private String PET_URL = "http://10.0.2.2:8080/pets/list";
    //  private String PET_TYPE_URL = "http://10.0.2.2:8080/petType/list";
    //  private String PET_GENDER_URL = "http://10.0.2.2:8080/petGender/list";
    //  private String RELATIONSHIP_URL = "http://10.0.2.2:8080/relationship/list";
    //  private String REGION_URL = "http://10.0.2.2:8080/regions/list";
    //  private String COMUNA_URL = "http://10.0.2.2:8080/comunas/list";
    //  private String COLOR_URL = "http://10.0.2.2:8080/colors/list";
    //  private String SIZE_URL = "http://10.0.2.2:8080/sizes/list";
    //  private String CONTEXTURE_URL = "http://10.0.2.2:8080/contextures/list";
    //  private String BREED_URL = "http://10.0.2.2:8080/breeds/list";
    //  private String PET_URL = "http://127.0.0.1:8080/pets/list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.found_lost);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(fetchAtpTennisReport());
            }
        });
        t.start();

        //Click event item ListView
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FoundLostActivity.this, PetDetailActivity.class);
                //Toast.makeText(FoundLostActivity.this,"position: " + position, Toast.LENGTH_SHORT).show();
                PetResponse petResponse = (PetResponse)parent.getAdapter().getItem(position);
                intent.putExtra("name", petResponse.name);
                intent.putExtra("idColor", petResponse.idColor);
                intent.putExtra("idSize", petResponse.idSize);
                intent.putExtra("idBreed", petResponse.idBreed);
                intent.putExtra("idPetType", petResponse.idPetType);
                intent.putExtra("idPetGender", petResponse.idPetGender);
                intent.putExtra("idContexture", petResponse.idContexture);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.searchPetItem:
                Intent i = new Intent(FoundLostActivity.this,FoundLostSearchActivity.class);
                startActivity(i);
                return true;
            case R.id.profileItem:
                i = new Intent(FoundLostActivity.this,MainMainActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
