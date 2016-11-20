package com.fabian.tallernov2016.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.fragments.LoginFragment;
import com.fabian.tallernov2016.models.User;
import com.fabian.tallernov2016.networking.Preferences;
import com.fabian.tallernov2016.networking.VolleyRequestSingleton;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = Preferences.getUser(this);
        if(user != null) {

            //Guarda los headers para los requests futuros
            VolleyRequestSingleton.getInstance(this).saveHeadersForFutureRequests(user);

            //Si existe un usuario con la sesion iniciada , pasa al MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {

            // Establece el view que contiene el contenedor para los fragment
            setContentView(R.layout.activity_landing);

            // android.R.id.content gives you the root element of a view,
            // without having to know its actual name/type/ID.
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new LoginFragment());
            transaction.commit();
        }
    }
}
