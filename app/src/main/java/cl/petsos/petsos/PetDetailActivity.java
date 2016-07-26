package cl.petsos.petsos;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class PetDetailActivity extends AppCompatActivity {
    private Button petFoundButton;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

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

            //add a listener when someone wants to report a found pet
            addListenerOnButtonReportPetFound();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void addListenerOnButtonReportPetFound() {
        petFoundButton = (Button) findViewById(R.id.btn_report);
        petFoundButton.setOnClickListener(reportPetFoundButtonListener);
    }

    private View.OnClickListener reportPetFoundButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //which pet was found


            //who found IdPerson

            //take a picture

            // get location
            takePhoto(view);
            Toast.makeText(PetDetailActivity.this,"Nombre: " , Toast.LENGTH_SHORT).show();
        }
    };


    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "perrito.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.lblDisplayImage);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }
}