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

            int idColor =  (int)bd.get("idColor");
            TextView textViewIdColor = (TextView)findViewById(R.id.textViewIdColorValue);
            textViewIdColor.setText((String.valueOf(idColor)));

            int idSize =  (int)bd.get("idSize");
            TextView textViewIdSize = (TextView)findViewById(R.id.textViewIdSizeValue);
            textViewIdSize.setText(String.valueOf(idSize));

            int idBreed =  (int)bd.get("idBreed");
            TextView textViewIdBreed = (TextView)findViewById(R.id.textViewIdBreedValue);
            textViewIdBreed.setText(String.valueOf(idBreed));

            int idPetType =  (int)bd.get("idPetType");
            TextView textViewIdPetType = (TextView)findViewById(R.id.textViewIdPetTypeValue);
            textViewIdPetType.setText(String.valueOf(idPetType));

            int idPetGender =  (int)bd.get("idPetGender");
            TextView textViewIdPetGender = (TextView)findViewById(R.id.textViewIdPetGenderValue);
            textViewIdPetGender.setText(String.valueOf(idPetGender));

            int idContexture =  (int)bd.get("idContexture");
            TextView textViewIdContexture = (TextView)findViewById(R.id.textViewIdContextureValue);
            textViewIdContexture.setText(String.valueOf(idContexture));



        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




}