package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.petsos.petsos.utils.PetSOSUtility;

/**
 * Created by root on 13-07-16.
 */
public class MainMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView usernameEditText;
    private TextView emailEditText;
    private TextView passwordEditText;

    private User user;
    private TextView btnLogout;

    private HashMap<String,String> genderMap = new HashMap<String,String>();
    private Spinner mGenderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //process Current User to give to him a suitable screen
        setUserInformation();
        logoutManager();
    }

    private void setUserInformation() {

        user=PrefUtils.getCurrentUser(MainMainActivity.this);

        LinearLayout linLay = (LinearLayout)findViewById(R.id.linLayRegUser);
        //set User fields

        if(user.getName() != null && !user.getName().trim().equals("")){
            usernameEditText = (TextView)findViewById(R.id.nameUserEditText);
            usernameEditText.setText(user.getName());

            addItemsOnGenderSpinner();


        }


        emailEditText = (TextView)findViewById(R.id.emailUserEditText);
        emailEditText.setText(user.getEmail());
        linLay.setVisibility(View.VISIBLE);

        //setContentView(linLay);
    }

    private void addItemsOnGenderSpinner() {
        mGenderSpinner = (Spinner)findViewById(R.id.genderList);
        List<String> genders = new ArrayList<String>();
        genders.add("Seleccione");
        genders.add("Femenino");
        genders.add("Masculino");
        //mGenderSpinner.setOnItemSelectedListener(genderListener);

        // getGenderList(user.getGender());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(dataAdapter);
       // mGenderSpinner.setOnItemSelectedListener();
        HashMap genderMap = new HashMap();
        genderMap.put("Selection", genders.get(0));
        genderMap.put("female", genders.get(1));
        genderMap.put("male", genders.get(2));
        if (!user.getGender().equals(null)) {

            int spinnerPosition = dataAdapter.getPosition((String)genderMap.get(user.getGender()));
            System.out.println("the position:"+spinnerPosition+"user.getGender():"+user.getGender());
            mGenderSpinner.setSelection(spinnerPosition);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getGenderList(String userGender) {
        /*ArrayAdapter<String> genderAdapter;
        genderMap.clear();


        genderMap.put("none", "Seleccione Genero");
        genderMap.put("female", "Femenino");
        genderMap.put("male", "Masculino");


        genderAdapter = new ArrayAdapter<String>(MainMainActivity.this,
                android.R.layout.simple_spinner_item, (List<String>) genderMap.values());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(genderAdapter);*/

     //   for(String keygender: genderMap.values()){
       //     if(keygender.equals(userGender)){



       // mGenderList = (Spinner) findViewById(R.id.genderList);
       // mGenderList.setOnItemSelectedListener(genderListener);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            findViewById(R.id.profile).setVisibility(View.VISIBLE);
            findViewById(R.id.foundlist).setVisibility(View.GONE);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            findViewById(R.id.profile).setVisibility(View.GONE);
            findViewById(R.id.foundlist).setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutManager(){
        btnLogout = (TextView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(MainMainActivity.this);


                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();


                Intent i= new Intent(MainMainActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private AdapterView.OnItemSelectedListener genderListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGender = (String)adapterView.getItemAtPosition(i);
                if(selectedGender.equals("female")){
                    System.out.println("female");
                }
                else if (selectedGender.equals("male")){
                    System.out.println("male");
                }
                else{
                    System.out.println("No male");
                }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

}