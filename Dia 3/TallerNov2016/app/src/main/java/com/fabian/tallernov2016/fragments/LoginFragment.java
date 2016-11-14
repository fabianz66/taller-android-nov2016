package com.fabian.tallernov2016.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.Utils;
import com.fabian.tallernov2016.activities.MainActivity;

/**
 * Ventana que permite al usuario iniciar sesión.
 *
 * También de la opción de ir a la ventana de Crear una nueva cuenta.
 *
 * Created by fabian on 11/5/16.
 */

public class LoginFragment extends Fragment {

    //region Atributos

    EditText mEditEmail;
    EditText mEditPassword;

    //endregion

    //region Ciclo de vida

    @Override
    public void onResume() {
        super.onResume();

        //Cambia el titulo de la ventana
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.title_login_screen);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Crea el View que se va a mostrar en este fragment.
        // Esto se hace a partir de un layout (XML).
        View view = inflater.inflate(R.layout.fragment_login, null);

        // Guarda los view para obtener los datos ingresados por el usuario posteriormente
        mEditEmail = (EditText) view.findViewById(R.id.et_email);
        mEditPassword = (EditText) view.findViewById(R.id.et_password);

        // Obtiene el botón de login y le asigna un evento
        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        // Obtiene el botón de register y le asigna un evento
        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showRegisterScreen();
            }
        });
        return view;
    }

    //endregion

    //region Manejo de eventos del usuario

    /**
     * Reemplaza el fragment actual (login) con el fragment para crear una nueva cuenta (register).
     */
    private void showRegisterScreen() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RegisterFragment());
        transaction.commit();
    }

    /**
     * Toma los datos ingresados por el usuario y verifica que el usuario exista
     * y el email-password coincidan.
     */
    private void attemptLogin() {

        // Reinicia los errores de los EditText
        mEditEmail.setError(null);
        mEditPassword.setError(null);

        // Obtiene los valores ingresados por el usuario
        String email = Utils.checkEditTextForEmpty(getContext(), mEditEmail);
        String password = Utils.checkEditTextForEmpty(getContext(), mEditPassword);

        //Revisa si hay valor vacío
        if (email == null || password == null) {
            return;
        }

        // Valida el correo
        if(!email.contains("@")) {
            mEditEmail.setError(getString(R.string.error_invalid_email));
            mEditEmail.requestFocus();
            return;
        }

        // Valida la contraseña
        if(password.length() < 3) {
            mEditPassword.setError(getString(R.string.error_invalid_password));
            mEditPassword.requestFocus();
            return;
        }

        // Intenta hacer login
        if(email.equals("f@f.com") && password.equals("123")) {

            //Abre la nueva activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            //Cierra la activity actual
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Correo/Contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    //endregion
}
