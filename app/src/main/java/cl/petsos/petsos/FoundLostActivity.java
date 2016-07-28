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
import android.widget.Button;
import android.widget.ListView;
import com.facebook.login.LoginManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public class FoundLostActivity extends AppCompatActivity {



    private String SERVER_URL = "https://petsos.herokuapp.com";
    private String PORT_URL   = "8080";
    private String PET_URL = SERVER_URL + "/pets/list";
    private Button addLostPetButton;

    /*
    private String SERVER_URL = "http://10.0.2.2";
    private String PORT_URL   = "8080";
    private String PET_URL = SERVER_URL + ":" + PORT_URL + "/pets/list";
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.found_lost);
        addListenerOnButtonAddPet();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(fetchPetList());
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
                intent.putExtra("color", petResponse.color);
                intent.putExtra("idSize", petResponse.idSize);
                intent.putExtra("size", petResponse.size);
                intent.putExtra("idBreed", petResponse.idBreed);
                intent.putExtra("breed", petResponse.breed);
                intent.putExtra("idPetType", petResponse.idPetType);
                intent.putExtra("petType", petResponse.petType);
                intent.putExtra("idPetGender", petResponse.idPetGender);
                intent.putExtra("petGender", petResponse.petGender);
                intent.putExtra("idContexture", petResponse.idContexture);
                intent.putExtra("contexture", petResponse.contexture);
                startActivity(intent);
            }
        });

    }

    private void addListenerOnButtonAddPet() {
        addLostPetButton = (Button)findViewById(R.id.button_add_lost_pet);
        addLostPetButton.setOnClickListener(addPetButtonListener);
    }

    private View.OnClickListener addPetButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent homeIntent = new Intent(FoundLostActivity.this, RegisterPetActivity.class);
            startActivity(homeIntent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.searchPetItem:
                Intent i = new Intent(FoundLostActivity.this,FoundLostSearchActivity.class);
                startActivity(i);
                return true;
            case R.id.profileItem:
                i = new Intent(FoundLostActivity.this,RegisterActivity.class);
                startActivity(i);
                return true;

            case R.id.logoutItem:
                PrefUtils.clearCurrentUser(FoundLostActivity.this);
                LoginManager.getInstance().logOut();
                i= new Intent(FoundLostActivity.this,MainActivity.class);
                startActivity(i);
                finish();
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


    private void backToMainThreadWithResponse(final List<PetResponse> response){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateListView(response);
            }
        });

    }

    private List<PetResponse> fetchPetList() {
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

    private void updateListView(List<PetResponse> response){

        ListView listview = (ListView)findViewById(R.id.listView);
        MyListViewAdapter adapter = new MyListViewAdapter(this);

        listview.setAdapter(adapter);
        adapter.setData(response);
    }

}
