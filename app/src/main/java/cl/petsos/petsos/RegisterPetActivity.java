package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import cl.petsos.petsos.utils.PetSOSUtility;

/**
 * Created by root on 21-07-16.
 */
public class RegisterPetActivity extends AppCompatActivity {
    private EditText petNameEditText;
    private Spinner petGenderSpinner;
    private Spinner petRelationshipSpinner;
    private Spinner petTypeSpinner;
    private Spinner petBreedSpinner;
    private Spinner petColorSpinner;
    private Spinner petSizeSpinner;
    private Spinner petBuildSpinner;
    private Spinner petStatusSpinner;
    private Button savePetDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pet);

        /*LLenar petRelationship*/
        Thread tPetRelationship = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPettRelationshipSpinner();
            }
        });
        tPetRelationship.start();

        /* llenar type*/
        Thread tPetType = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetTypeSpinner();
            }
        });
        tPetType.start();

        /*LLenar petGender*/
        Thread tPetGender = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetGenderSpinner();
            }
        });
        tPetGender.start();

        /* llenar Raza*/
        Thread tPetBreed = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetBreedSpinner();
            }
        });
        tPetBreed.start();

        /*Color*/
        Thread tPetColor = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetColorSpinner();
            }
        });
        tPetColor.start();

        /*Estatura*/

        Thread tPetSize = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetSizeSpinner();
            }
        });
        tPetSize.start();

        /*Build*/
        Thread tPetBuild = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetBuildSpinner();
            }
        });
        tPetBuild.start();

        /*Status*/
        Thread tPetStatus = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetStatusSpinner();
            }
        });
        tPetStatus.start();
        addListenerOnButtonSavePetData();
    }

    private void addListenerOnButtonSavePetData() {
        savePetDataButton = (Button) findViewById(R.id.button_save_pet_reg);
        savePetDataButton.setOnClickListener(savePetDataButtonListener);
    }

    private View.OnClickListener savePetDataButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //check if we have all the information before save
            if(validatePetDataIsCompleted()) {
                //TODO save pet

               // Toast.makeText(RegisterPetActivity.this,"Nombre: " , Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean validatePetDataIsCompleted() {
        //name
        petNameEditText = (EditText) findViewById(R.id.petNameEditText);
        String pPetName = petNameEditText.getText().toString();
        //relationship
        petRelationshipSpinner = (Spinner) findViewById(R.id.petRelationList);
        String pRelationship = petRelationshipSpinner.getSelectedItem().toString();

        petTypeSpinner = (Spinner) findViewById(R.id.petTypeList);
        String ptype = petTypeSpinner.getSelectedItem().toString();

        petGenderSpinner = (Spinner) findViewById(R.id.petGeneroList);
        String pgender = petGenderSpinner.getSelectedItem().toString();

        petBreedSpinner = (Spinner) findViewById(R.id.petBreedList);
        String pbreed = petBreedSpinner.getSelectedItem().toString();

        petColorSpinner = (Spinner) findViewById(R.id.petColorList);
        String pcolor = petColorSpinner.getSelectedItem().toString();

        petSizeSpinner = (Spinner) findViewById(R.id.petSizeList);
        String psize = petSizeSpinner.getSelectedItem().toString();

        petBuildSpinner = (Spinner) findViewById(R.id.petBuildList);
        String pbuild = petBuildSpinner.getSelectedItem().toString();

        petStatusSpinner = (Spinner) findViewById(R.id.petStatusList);
        String pstatus = petStatusSpinner.getSelectedItem().toString();

        //gender
       /* String gender= mGenderSpinner.getSelectedItem().toString();
        String genderMapped = "";
        //transform the gender as facebook way (female/male)
        genderMapped = PetSOSUtility.getPetSOSUtility().getGenderUserMapper(gender, genderMapped);*/
        Toast.makeText(RegisterPetActivity.this,"Nombre: " +pPetName+ " "+pRelationship + " "+ptype+ " "+psize, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void addItemsOnPetStatusSpinner() {
        petStatusSpinner = (Spinner) findViewById(R.id.petStatusList);
        String[] petStatus = PetSOSUtility.getPetSOSUtility().getPetStatus();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petStatusSpinner.setAdapter(adapter);
            }
        });

    }

    private void addItemsOnPetBuildSpinner() {
        petBuildSpinner = (Spinner) findViewById(R.id.petBuildList);
        List<String> petBuilds = PetSOSUtility.getPetSOSUtility().getPetBuilds();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petBuilds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petBuildSpinner.setAdapter(adapter);
            }
        });

    }

    private void addItemsOnPetSizeSpinner() {

        petSizeSpinner = (Spinner) findViewById(R.id.petSizeList);
        List<String> petSizes = PetSOSUtility.getPetSOSUtility().getPetSizes();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petSizeSpinner.setAdapter(adapter);
            }
        });

    }

    private void addItemsOnPetColorSpinner() {
        petColorSpinner = (Spinner) findViewById(R.id.petColorList);
        List<String> petColors = PetSOSUtility.getPetSOSUtility().getPetColors();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petColors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petColorSpinner.setAdapter(adapter);
            }
        });

    }

    private void addItemsOnPetBreedSpinner() {
        petBreedSpinner = (Spinner) findViewById(R.id.petBreedList);
        List<String> petBreeds = PetSOSUtility.getPetSOSUtility().getPetBreeds();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petBreeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petBreedSpinner.setAdapter(adapter);
            }
        });

    }

    private void addItemsOnPetTypeSpinner() {
        petTypeSpinner = (Spinner) findViewById(R.id.petTypeList);
        List<String> petTypes = PetSOSUtility.getPetSOSUtility().getPetTypes();

        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petTypeSpinner.setAdapter(typeAdapter);
            }
        });
    }

    private void addItemsOnPettRelationshipSpinner() {
        petRelationshipSpinner = (Spinner) findViewById(R.id.petRelationList);
        List<String> petRelationships = PetSOSUtility.getPetSOSUtility().getRelationshipPet();

        final ArrayAdapter<String> relationshipAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petRelationships);
        relationshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petRelationshipSpinner.setAdapter(relationshipAdapter);
            }
        });
    }

    private void addItemsOnPetGenderSpinner() {
        petGenderSpinner = (Spinner) findViewById(R.id.petGeneroList);
        List<String> petGenders = PetSOSUtility.getPetSOSUtility().getGendersPet();

        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petGenders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                petGenderSpinner.setAdapter(genderAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.searchPetItem:
                Intent i = new Intent(RegisterPetActivity.this,FoundLostSearchActivity.class);
                startActivity(i);
                return true;
            case R.id.profileItem:
                i = new Intent(RegisterPetActivity.this,RegisterActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
