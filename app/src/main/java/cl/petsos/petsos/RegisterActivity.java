package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

import cl.petsos.petsos.utils.PetSOSUtility;


public class RegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private User user;
    private Region regionUser;
    private Comuna comunaUser;

    private TextView btnLogout;
    private Button saveUserDataButton;
    private Button updateUserDataButton;
    private Button addPetButton;
    private TextView searchPetsLinkTextView;

    private HashMap<String,String> genderMap;
    private HashMap<String,List<String>> regionMap = new HashMap<String,List<String>>();
    private HashMap<String,List<String>> comunaMap = new HashMap<String,List<String>>();
    private Spinner mGenderSpinner;
    private Spinner mRegionSpinner;
    private Spinner mComunaSpinner;
    private boolean existingUser;

    MenuItem menuItLProfile;
    MenuItem menuItListPets;
    MenuItem menuItSearchPet;
    MenuItem menuItScanQrItem;

    List<String> comunas  = new ArrayList<String>( );
    List<String> genders = new ArrayList<String>( );
    List<String> regionList = new ArrayList<String>();

    ArrayAdapter<String> regionAdapter;
    ArrayAdapter<String> comunasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        initializeUserFormViews();
        initializeListeners();

        existingUser = PrefUtils.isExistingUser(RegisterActivity.this);
        user= getUserToSetFormData();
        if(existingUser){
            setCurrentUserInformation(usernameEditText,emailEditText);
            User currentUser = PrefUtils.getCurrentUser(RegisterActivity.this);
            boolean registroUsuarioCompleto = PetSOSUtility.getPetSOSUtility().isUserRegisterComplete(currentUser);

            if(registroUsuarioCompleto){
               setExistingUserViews();
            }
            else{
               setHiddenMenuItems();
            }
        }
        else{
            setCleanUserInformation(usernameEditText,emailEditText);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addItemsOnGenderSpinner();
                addItemsOnRegionSpinner();
            }
        });

        logoutManager();
    }

    private void initializeUserFormViews() {
        usernameEditText = (EditText) findViewById(R.id.nameUserEditText);
        emailEditText = (EditText)findViewById(R.id.emailUserEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordUserEditText);
    }

    private void initializeMenuItems(Menu menu) {
        menuItLProfile = menu.findItem(R.id.profileItem);
        menuItListPets = menu.findItem(R.id.listPetsItem);
        menuItSearchPet = menu.findItem(R.id.searchPetItem);
        menuItScanQrItem = menu.findItem(R.id.scanQrItem);
    }

    private void addListenerOnButtonUpdateUserData() {
        updateUserDataButton = (Button) findViewById(R.id.button_update_user_reg);
        updateUserDataButton.setOnClickListener(updateUserDataButtonListener);
        searchPetsLinkTextView = (TextView)findViewById(R.id.searchPetsLink);
        searchPetsLinkTextView.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener updateUserDataButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            //TODO
            System.out.println("Lets update an existing user");
        }
    };

    private void addListenerOnButtonAddPet() {
        addPetButton = (Button)findViewById(R.id.button_add_pet);
        addPetButton.setOnClickListener(addPetButtonListener);
    }

    private View.OnClickListener addPetButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent homeIntent = new Intent(RegisterActivity.this, RegisterPetActivity.class);
            startActivity(homeIntent);
        }
    };

    private void addListenerOnSearchPetsLink() {
        searchPetsLinkTextView = (TextView)findViewById(R.id.searchPetsLink);
        searchPetsLinkTextView.setOnClickListener(searchPetsLinkListener);
    }

    private View.OnClickListener searchPetsLinkListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent homeIntent = new Intent(RegisterActivity.this, FoundLostActivity.class);
            startActivity(homeIntent);
        }
    };

    private void addListenerOnButtonSaveUserData() {
        saveUserDataButton = (Button) findViewById(R.id.button_save_user_reg);
        saveUserDataButton.setOnClickListener(saveUserDataButtonListener);
    }

    private View.OnClickListener saveUserDataButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateDataIsCompleted()) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int responseCode = PetSOSUtility.getPetSOSUtility().createUser(user);
                        if(responseCode == 200){
                            PrefUtils.setCurrentUser(user, RegisterActivity.this);
                            PrefUtils.setExistingUser(true,RegisterActivity.this);
                            Toast.makeText(RegisterActivity.this,"User Created succesfully", Toast.LENGTH_SHORT).show();
                            setExistingUserViews();
                            searchPetsLinkTextView.setVisibility(View.VISIBLE);
                            setVisibleMenuItems();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Error When User tried to be Created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Toast.makeText(RegisterActivity.this,"Nombre: " + PrefUtils.getCurrentUser(RegisterActivity.this).getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setExistingUserViews() {
        addPetButton = (Button) findViewById(R.id.button_add_pet);
        addPetButton.setVisibility(View.VISIBLE);
        saveUserDataButton.setVisibility(View.GONE);
        updateUserDataButton.setVisibility(View.VISIBLE);
        emailEditText.setEnabled(false);
    }

    private boolean validateDataIsCompleted(){
        String userName = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        String pass = passwordEditText.getText().toString();

        String gender= mGenderSpinner.getSelectedItem().toString();
        String genderMapped = "";
        //transform the gender as facebook way (female/male)
        genderMapped = PetSOSUtility.getPetSOSUtility().getGenderUserMapper(gender, genderMapped);

        String userReg= mRegionSpinner.getSelectedItem().toString();
        String userComuna= mComunaSpinner.getSelectedItem().toString();

        if(allFieldsUserFormAreFilled(userName, email, pass, genderMapped, userReg, userComuna)){
            setTempDataUser(userName,email, genderMapped, pass, userReg, userComuna );
            return true;
        }
        return false;
    }

    private void setTempDataUser(String userName, String email, String genderMapped, String pass, String userReg, String userComuna ){

            //user= getUserToSetFormData();
            user.setName(userName);
            user.setEmail(email);
            GenderUser myGender = new GenderUser();
            myGender.setGenderName(genderMapped);
            user.setGender(myGender);
            user.setPassword(pass);

            Comuna myComuna = new Comuna();
            myComuna.setComunaName(userComuna);

            Region myReg = new Region();
            myReg.setRegionName(userReg);
            myComuna.setRegion(myReg);
            user.setComuna(myComuna);
    }

    private User getUserToSetFormData(){
        existingUser = PrefUtils.isExistingUser(RegisterActivity.this);
        User user;
        if(!existingUser){
            user = new User();
        }else{
            user=PrefUtils.getCurrentUser(RegisterActivity.this);
        }
        return user;
    }

    private boolean allFieldsUserFormAreFilled(String userName, String email, String pass, String genderMapped, String userReg, String userComuna) {
        return userName != null && !userName.trim().equals("")
                && email != null && !email.trim().equals("")
                && genderMapped != null && !genderMapped.trim().equals("") && !genderMapped.trim().equals("Seleccione")
                && pass != null && !pass.trim().equals("")
                && userReg != null && !userReg.trim().equals("") && !userReg.trim().equals("Seleccione")
                && userComuna != null && !userComuna.trim().equals("") && !userComuna.trim().equals("Seleccione");
    }

    private void setCurrentUserInformation(EditText usernameEditText, EditText emailEditText) {
        user=PrefUtils.getCurrentUser(RegisterActivity.this);

        boolean existsCurrentUserName = user != null && user.getName() != null && !user.getName().trim().equals("");
        boolean existsCurrentEmail = user != null && user.getEmail() != null && !user.getEmail().trim().equals("");
        boolean existsCurrentPassword = user != null && user.getPassword() != null && !user.getPassword().trim().equals("");

        if(existsCurrentUserName){
            usernameEditText.setText(user.getName());
        }

        if(existsCurrentEmail){
            emailEditText.setText(user.getEmail());
        }

        if(existsCurrentPassword){
            passwordEditText.setText(user.getPassword());
        }

    }

    private void setCleanUserInformation(EditText usernameEditText, EditText emailEditText){
        usernameEditText.setText("");
        emailEditText.setText("");
        searchPetsLinkTextView.setVisibility(View.GONE);
    }

    private void addItemsOnRegionSpinner() {

        //init populate region comunas
        if(comunas.size() == 0) {
            comunas.add("Seleccione");
            regionMap.put("Seleccione", comunas);

        }

        if(regionList.size() == 0){
            Thread tRegiones = new Thread(new Runnable() {
                @Override
                public void run() {
                    getRegionesForSpinner();
                }
            });
            tRegiones.start();
        }

    }

    private void getRegionesForSpinner() {

        regionList = PetSOSUtility.getPetSOSUtility().getAllRegiones();

        mRegionSpinner = (Spinner) findViewById(R.id.regionList);
        mRegionSpinner.setOnItemSelectedListener(regionListener);
        mComunaSpinner = (Spinner) findViewById(R.id.comunaList);
        mComunaSpinner.setOnItemSelectedListener(comunaListener);

        regionAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, regionList);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        comunasAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, comunas);
        comunasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRegionSpinner.setAdapter(regionAdapter);

                mComunaSpinner.setAdapter(comunasAdapter);

                if (user !=null && user.getComuna() != null &&  user.getComuna().getComunaName() != null &&
                        user.getComuna().getRegion() != null && user.getComuna().getRegion().getRegionName()!=null) {
                    String regName = user.getComuna().getRegion().getRegionName();
                    Integer regId = user.getComuna().getRegion().getRegionId();
                    int regSpinnerPosition = regionAdapter.getPosition(regName);
                    mRegionSpinner.setSelection(regSpinnerPosition);


                    comunas = PetSOSUtility.getPetSOSUtility().getAllComunasByRegionId(regId);

                    int comSpinnerPosition = comunasAdapter.getPosition(user.getComuna().getComunaName());
                    mComunaSpinner.setSelection(comSpinnerPosition);

                }
                else{
                    int spinnerPosition = regionAdapter.getPosition("Seleccione");
                    mRegionSpinner.setSelection(spinnerPosition);
                }
            }
        });

    }

    private AdapterView.OnItemSelectedListener regionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            final String selectedRegion = (String) adapterView.getItemAtPosition(i);
            if(!selectedRegion.equals("Seleccione")){

                regionUser = new Region();
                regionUser.setRegionName(selectedRegion);
                regionUser.setRegionId(PetSOSUtility.getPetSOSUtility().getIdRegionByRegioName(selectedRegion));
                comunaUser = new Comuna();
                comunaUser.setRegion(regionUser);
                user.setComuna(comunaUser);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int regSpinnerPosition = regionAdapter.getPosition(selectedRegion);
                        mRegionSpinner.setSelection(regSpinnerPosition);

                        comunas = PetSOSUtility.getPetSOSUtility().getAllComunasByRegionId(regionUser.getRegionId());

                        comunasAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                                android.R.layout.simple_spinner_item, comunas);
                        comunasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //mRegionSpinner.setAdapter(regionAdapter);

                        mComunaSpinner.setAdapter(comunasAdapter);

                        if(comunas.size() >0 && user != null && user.getComuna()!= null && user.getComuna().getComunaName() != null ){
                            int comSpinnerPosition = comunasAdapter.getPosition(user.getComuna().getComunaName());
                            mComunaSpinner.setSelection(comSpinnerPosition);
                        }

                        else{
                            int spinnerPosition = comunasAdapter.getPosition("Seleccione");
                            mComunaSpinner.setSelection(spinnerPosition);
                        }
                    }
                });

            }

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
        mGenderSpinner = (Spinner) findViewById(R.id.genderList);
        if (genders.size() == 0) {
            Thread tGenderUser = new Thread(new Runnable() {
                @Override
                public void run() {
                    getGenderUserForSpinner();
                }
            });
            tGenderUser.start();
        }
    }

    private void getGenderUserForSpinner(){

        genders = PetSOSUtility.getPetSOSUtility().getGendersUser();

        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mGenderSpinner.setAdapter(genderAdapter);
                genderMap = PetSOSUtility.getPetSOSUtility().getGendersUserHashMap(genders);

                //setting the gender coming from facebook
                boolean existsCurrentUserGender = user != null && user.getGender() != null && !user.getGender().equals(null);

                if (existsCurrentUserGender) {
                    int spinnerPosition = genderAdapter.getPosition((String)genderMap.get(user.getGender().getGenderName()));
                    mGenderSpinner.setSelection(spinnerPosition);
                }
                else{
                    mGenderSpinner.setSelection(0);
                }
            }});



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
        existingUser = PrefUtils.isExistingUser(RegisterActivity.this);

        initializeMenuItems(menu);

        if(!existingUser){
            setHiddenMenuItems();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.listPetsItem:
                Intent i = new Intent(RegisterActivity.this,FoundLostActivity.class);
                startActivity(i);
                return true;
            case R.id.searchPetItem:
                i = new Intent(RegisterActivity.this,FoundLostSearchActivity.class);
                startActivity(i);
                return true;
            case R.id.logoutItem:
                //Toast.makeText(RegisterActivity.this,"logout",Toast.LENGTH_SHORT).show();
                //i = new Intent(RegisterActivity.this,FoundLostSearchActivity.class);
                //startActivity(i);
                PrefUtils.clearCurrentUser(RegisterActivity.this);
                LoginManager.getInstance().logOut();
                i= new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                return true;

        }

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

            Intent i = new Intent(RegisterActivity.this,FoundLostActivity.class);
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
                PrefUtils.clearCurrentUser(RegisterActivity.this);


                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();


                Intent i= new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setHiddenMenuItems() {
        menuItLProfile.setVisible(false);
        menuItListPets.setVisible(false);
        menuItSearchPet.setVisible(false);
        menuItScanQrItem.setVisible(false);
    }

    private void setVisibleMenuItems() {
        menuItLProfile.setVisible(true);
        menuItListPets.setVisible(true);
        menuItSearchPet.setVisible(true);
        menuItScanQrItem.setVisible(true);
    }

    private void initializeListeners() {
        addListenerOnButtonUpdateUserData();
        addListenerOnButtonAddPet();
        addListenerOnSearchPetsLink();
        addListenerOnButtonSaveUserData();
    }
}