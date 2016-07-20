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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cl.petsos.petsos.utils.PetSOSUtility;

/**
 * Created by root on 13-07-16.
 */
public class MainMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private User user;
    private TextView btnLogout;
    private Button saveUserDataButton;

    private HashMap<String,String> genderMap;
    private HashMap<String,List<String>> regionMap = new HashMap<String,List<String>>();
    private HashMap<String,List<String>> comunaMap = new HashMap<String,List<String>>();
    private Spinner mGenderSpinner;
    private Spinner mRegionSpinner;
    private Spinner mComunaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/


        //process Current User to give to him a suitable screen
        setUserInformation();
        addListenerOnButtonSaveUserData();
        logoutManager();
    }

    private void addListenerOnButtonSaveUserData() {
        saveUserDataButton = (Button) findViewById(R.id.button_save_user_reg);
        saveUserDataButton.setOnClickListener(saveUserDataButtonListener);
    }

    private View.OnClickListener saveUserDataButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //check if we have all the information before save
            if(validateDataIsCompleted()) {
                //TODO save user
                Button addPetButton = (Button) findViewById(R.id.button_add_pet);

                addPetButton.setVisibility(View.VISIBLE);

                Toast.makeText(MainMainActivity.this,"Nombre: " + PrefUtils.getCurrentUser(MainMainActivity.this).getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean validateDataIsCompleted(){
        //name
        String userName = usernameEditText.getText().toString();
        //email
        String email = emailEditText.getText().toString();
        //password
        passwordEditText = (EditText)findViewById(R.id.passwordUserEditText); //()passwordEditText
        String pass = passwordEditText.getText().toString();

        //gender
        String gender= mGenderSpinner.getSelectedItem().toString();
        String genderMapped = "";
        //transform the gender as facebook way (female/male)
        genderMapped = PetSOSUtility.getPetSOSUtility().getGenderMapper(gender, genderMapped);

        //region
        String userReg= mRegionSpinner.getSelectedItem().toString();
        //comuna
        String userComuna= mComunaSpinner.getSelectedItem().toString();

        if(userName != null && !userName.trim().equals("")
                && email != null && !email.trim().equals("")
                && genderMapped != null && !genderMapped.trim().equals("") && !genderMapped.trim().equals("Seleccione")
                && pass != null && !pass.trim().equals("")
                && userReg != null && !userReg.trim().equals("") && !userReg.trim().equals("Seleccione")
                && userComuna != null && !userComuna.trim().equals("") && !userComuna.trim().equals("Seleccione")){

            setDataUser(userName,email, genderMapped, pass, userReg, userComuna );

            return true;
        }
        return false;
    }

    private void setDataUser(String userName, String email, String genderMapped, String pass, String userReg, String userComuna ){

            user=PrefUtils.getCurrentUser(MainMainActivity.this);
            user.setName(userName);
            user.setEmail(email);
            user.setGender(genderMapped);
            user.setPassword(pass);

            int userRegId = PetSOSUtility.getPetSOSUtility().getIdRegionByRegioName(userReg);
            int userComunaId = PetSOSUtility.getPetSOSUtility().getIdComunaByComunaName(userComuna);
            UserComuna mUserComuna = new UserComuna();
            mUserComuna.setIdComuna(userComunaId);
            mUserComuna.setIdPerson(1);
            List<UserComuna> mUserComunas = new ArrayList<UserComuna>();
            mUserComunas.add(mUserComuna);
            user.setUserComunas(mUserComunas);
            user.setId_person(1);

            PrefUtils.setCurrentUser(user,MainMainActivity.this);
    }


    private void setUserInformation() {

        user=PrefUtils.getCurrentUser(MainMainActivity.this);

        LinearLayout linLay = (LinearLayout)findViewById(R.id.linLayRegUser);
        //set User fields

        if(user.getName() != null && !user.getName().trim().equals("")){
            usernameEditText = (EditText) findViewById(R.id.nameUserEditText);
            usernameEditText.setText(user.getName());
        }

        emailEditText = (EditText)findViewById(R.id.emailUserEditText);
        emailEditText.setText(user.getEmail());

        addItemsOnGenderSpinner();
        addItemsOnRegionSpinner();

        linLay.setVisibility(View.VISIBLE);

        //setContentView(linLay);
    }

    private void addItemsOnRegionSpinner() {

        //init populate region comunas
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
        //end populate region comunas

        mRegionSpinner = (Spinner) findViewById(R.id.regionList);
        mRegionSpinner.setOnItemSelectedListener(regionListener);
        mComunaSpinner = (Spinner) findViewById(R.id.comunaList);
        mComunaSpinner.setOnItemSelectedListener(comunaListener);

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(MainMainActivity.this,
                android.R.layout.simple_spinner_item, regionList);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionSpinner.setAdapter(regionAdapter);

        if ( user.getUserComunas().size() > 0 ) {
          //  int spinnerPosition = dataAdapter.getPosition((String)genderMap.get(user.getGender()));
          //  mGenderSpinner.setSelection(spinnerPosition);
        }
        else{
            int spinnerPosition = regionAdapter.getPosition("Seleccione");
            mRegionSpinner.setSelection(spinnerPosition);
        }
    }

    private AdapterView.OnItemSelectedListener regionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedCountry = (String) adapterView.getItemAtPosition(i);
            List<String> comunaList = new ArrayList<String>();
            comunaList = regionMap.get(selectedCountry);

            ArrayAdapter<String> comunasAdapter = new ArrayAdapter<String>(MainMainActivity.this,
                   android.R.layout.simple_spinner_item, comunaList);
            comunasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mComunaSpinner.setAdapter(comunasAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener comunaListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void addItemsOnGenderSpinner() {
        mGenderSpinner = (Spinner)findViewById(R.id.genderList);
        List<String> genders = new ArrayList<String>();
        genders.add("Seleccione");
        genders.add("Femenino");
        genders.add("Masculino");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(dataAdapter);

        HashMap<String,String> genderMap = new HashMap<String,String>();
        genderMap.put("Selection", genders.get(0));
        genderMap.put("female", genders.get(1));
        genderMap.put("male", genders.get(2));

        //setting the gender coming from facebook
        if (!user.getGender().equals(null)) {
            int spinnerPosition = dataAdapter.getPosition((String)genderMap.get(user.getGender()));
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            LinearLayout linLay = (LinearLayout)findViewById(R.id.linLayRegUser);
            linLay.setVisibility(View.GONE);
            findViewById(R.id.foundlist).setVisibility(View.GONE);
            // Handle the camera action
        }
        if (id == R.id.nav_camera2) {
            findViewById(R.id.datos_usuario_lay).setVisibility(View.VISIBLE);
            LinearLayout linLay = (LinearLayout)findViewById(R.id.linLayRegUser);
            linLay.setVisibility(View.VISIBLE);
            findViewById(R.id.foundlist).setVisibility(View.GONE);
            findViewById(R.id.profile).setVisibility(View.GONE);
            // Handle the camera action
        }/*else if (id == R.id.nav_gallery) {
            findViewById(R.id.datos_usuario_lay).setVisibility(View.GONE);
            findViewById(R.id.foundlist).setVisibility(View.VISIBLE);

        }*/ else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.foundLostItem) {

            Intent i = new Intent(MainMainActivity.this,FoundLostActivity.class);
            startActivity(i);
        }

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        */
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

}