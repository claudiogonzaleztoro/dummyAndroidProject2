package cl.petsos.petsos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;

/**
 * Created by root on 11-07-16.
 */
public class RegisterUser extends AppCompatActivity {
    private TextView usernameEditText;
    private TextView emailEditText;
    private TextView passwordEditText;

    private User user;
    private TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

       /* Toolbar petsosToolbar = (Toolbar) findViewById(R.id.petsos_toolbar);
        setSupportActionBar(petsosToolbar);
        petsosToolbar.setNavigationIcon(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);*/
        ActionBar actBar = getSupportActionBar();


        user=PrefUtils.getCurrentUser(RegisterUser.this);

        LinearLayout linLay = (LinearLayout)findViewById(R.id.linLayRegUser);
        //set User fields

        if(user.getName() != null && !user.getName().trim().equals("")){
            usernameEditText = (TextView)findViewById(R.id.nameUserEditText);
            usernameEditText.setText(user.getName());
        }


        emailEditText = (TextView)findViewById(R.id.emailUserEditText);
        emailEditText.setText(user.getEmail());


        setContentView(linLay);



        btnLogout = (TextView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(RegisterUser.this);


                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();


                Intent i= new Intent(RegisterUser.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
