package com.fabian.tallernov2016.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fabian.tallernov2016.fragments.LoginFragment;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // android.R.id.content gives you the root element of a view,
        // without having to know its actual name/type/ID.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, new LoginFragment());
        transaction.commit();
    }
}
