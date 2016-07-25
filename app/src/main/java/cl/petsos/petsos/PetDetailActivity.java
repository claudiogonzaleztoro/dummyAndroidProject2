package cl.petsos.petsos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;


public class PetDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_detail_layout);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
        {
            String name = (String) bd.get("name");
            TextView textViewName = (TextView)findViewById(R.id.textViewNamePetValue);
            textViewName.setText(name);

            /*
            int idColor =  (int)bd.get("idColor");
            TextView textViewIdColor = (TextView)findViewById(R.id.textViewIdColorValue);
            textViewIdColor.setText((String.valueOf(idColor)));
            */

            String color =  (String)bd.get("color");
            TextView textViewIdColor = (TextView)findViewById(R.id.textViewIdColorValue);
            textViewIdColor.setText((String.valueOf(color)));

            String size =  (String)bd.get("size");
            TextView textViewIdSize = (TextView)findViewById(R.id.textViewIdSizeValue);
            textViewIdSize.setText(String.valueOf(size));

            String idBreed =  (String)bd.get("breed");
            TextView textViewIdBreed = (TextView)findViewById(R.id.textViewIdBreedValue);
            textViewIdBreed.setText(String.valueOf(idBreed));

            String petType =  (String)bd.get("petType");
            TextView textViewIdPetType = (TextView)findViewById(R.id.textViewIdPetTypeValue);
            textViewIdPetType.setText(String.valueOf(petType));

            String petGender =  (String)bd.get("petGender");
            TextView textViewIdPetGender = (TextView)findViewById(R.id.textViewIdPetGenderValue);
            textViewIdPetGender.setText(String.valueOf(petGender));

            String contexture =  (String)bd.get("contexture");
            TextView textViewIdContexture = (TextView)findViewById(R.id.textViewIdContextureValue);
            textViewIdContexture.setText(String.valueOf(contexture));



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




}