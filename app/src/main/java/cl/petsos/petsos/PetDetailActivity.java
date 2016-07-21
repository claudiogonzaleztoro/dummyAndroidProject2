package cl.petsos.petsos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        TextView textView = (TextView)findViewById(R.id.textViewNamePet);
        textView.setText("Name: " + name);
        //textView.setText(Html.fromHtml("Name   &nbsp;&nbsp;<u><b>perros</b></u>   &nbsp;&nbsp;<u><b>machos</b></u> &nbsp;&nbsp;<u><b>ConDueño</b></u> &nbsp;&nbsp;de <u><b>Ñuñoa</b></u> &nbsp;&nbsp;color &nbsp;&nbsp;<u><b>Blanco</b></u> &nbsp;&nbsp;tamaño &nbsp;&nbsp;<u><b>Chico</b></u> contextura &nbsp;&nbsp;<u><b>media</b></u> &nbsp;&nbsp;raza &nbsp;&nbsp;<u><b>Kiltro</b></u>"));

        int idColor =  (int)bd.get("idColor");
        TextView textViewIdColor = (TextView)findViewById(R.id.textViewIdColor);
        textViewIdColor.setText("Color: " + idColor);

        int idSize =  (int)bd.get("idSize");
        TextView textViewIdSize = (TextView)findViewById(R.id.textViewIdSize);
        textViewIdSize.setText("Size: " + idSize);

        int idBreed =  (int)bd.get("idBreed");
        TextView textViewIdBreed = (TextView)findViewById(R.id.textViewIdBreed);
        textViewIdBreed.setText("Breed: " + idBreed);

        int idPetType =  (int)bd.get("idPetType");
        TextView textViewIdPetType = (TextView)findViewById(R.id.textViewIdPetType);
        textViewIdPetType.setText("IdPetType: " + idPetType);

        int idPetGender =  (int)bd.get("idPetGender");
        TextView textViewIdPetGender = (TextView)findViewById(R.id.textViewIdPetGender);
        textViewIdPetGender.setText("idPetGender: " + idPetGender);

        int idContexture =  (int)bd.get("idContexture");
        TextView textViewIdContexture = (TextView)findViewById(R.id.textViewIdContexture);
        textViewIdContexture.setText("idContexture: " + idContexture);



    }

}






}