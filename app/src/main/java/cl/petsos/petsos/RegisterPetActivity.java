package cl.petsos.petsos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;

import cl.petsos.petsos.utils.PetSOSUtility;

/**
 * Created by root on 21-07-16.
 */
public class RegisterPetActivity extends Activity {
    private Spinner petGenderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pet);

        /*LLenar petGender*/
        Thread tPetGender = new Thread(new Runnable() {
            @Override
            public void run() {
                addItemsOnPetGenderSpinner();
            }
        });
        tPetGender.start();

    }


    private void addItemsOnPetGenderSpinner() {
        petGenderSpinner = (Spinner) findViewById(R.id.petGeneroList);
        List<String> petGenders = PetSOSUtility.getPetSOSUtility().getGendersPet();

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petGenders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petGenderSpinner.setAdapter(genderAdapter);
        petGenderSpinner.setSelection(0);
    }
}
