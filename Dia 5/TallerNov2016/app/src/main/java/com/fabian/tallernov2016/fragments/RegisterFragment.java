package com.fabian.tallernov2016.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fabian.tallernov2016.models.User;
import com.fabian.tallernov2016.networking.BackendAccess;

/**
 * Created by fabian on 11/5/16.
 */

public class RegisterFragment extends Fragment {

    //region Atributos

    EditText mEditEmail;
    EditText mEditFirstName;
    EditText mEditLastName;
    EditText mEditPassword;
    EditText mEditPasswordConfirm;
    BackendAccess mBackendAccess;

    //endregion

    //region Ciclo de vida

    @Override
    public void onResume() {
        super.onResume();

        //Cambia el titulo de la ventana
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_register_screen);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackendAccess = new BackendAccess(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Crea el View que se va a mostrar en este fragment.
        // Esto se hace a partir de un layout (XML).
        View view = inflater.inflate(R.layout.fragment_register, null);

        // Guarda los view para obtener los datos ingresados por el usuario posteriormente
        mEditEmail = (EditText) view.findViewById(R.id.et_email);
        mEditFirstName = (EditText) view.findViewById(R.id.et_firstName);
        mEditLastName = (EditText) view.findViewById(R.id.et_lastName);
        mEditPassword = (EditText) view.findViewById(R.id.et_password);
        mEditPasswordConfirm = (EditText) view.findViewById(R.id.et_confirmPassword);

        // Obtiene el botón de register y le asigna un evento
        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        // Obtiene el botón de login y le asigna un evento
        view.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showLoginScreen();
            }
        });
        return view;
    }

    //endregion

    //region Manejo de eventos del usuario

    /**
     * Reemplaza el fragment actual (Register) con el fragment para crear iniciar sesión (Login)
     */
    private void showLoginScreen() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoginFragment());
        transaction.commit();
    }

    /**
     * Toma los datos ingresados por el usuario y verifica que el usuario exista
     * y el email-password coincidan.
     */
    private void attemptRegister() {

        // Reinicia los errores de los EditText
        mEditEmail.setError(null);
        mEditFirstName.setError(null);
        mEditLastName.setError(null);
        mEditPassword.setError(null);
        mEditPasswordConfirm.setError(null);

        // Obtiene los valores ingresados por el usuario
        String email = Utils.checkEditTextForEmpty(getContext(), mEditEmail);
        String firstName = Utils.checkEditTextForEmpty(getContext(), mEditFirstName);
        String lastName = Utils.checkEditTextForEmpty(getContext(), mEditLastName);
        String password = Utils.checkEditTextForEmpty(getContext(), mEditPassword);
        String passwordConfirm = Utils.checkEditTextForEmpty(getContext(), mEditPasswordConfirm);

        //Revisa si hay valor vacío
        if (email == null || firstName == null || lastName == null || password == null || passwordConfirm == null) {
            return;
        }

        // Valida el correo
        if (!email.contains("@")) {
            mEditEmail.setError(getString(R.string.error_invalid_email));
            mEditEmail.requestFocus();
            return;
        }

        // Valida la contraseña
        if (password.length() < 3) {
            mEditPassword.setError(getString(R.string.error_invalid_password));
            mEditPassword.requestFocus();
            return;
        }

        //Valida que las contraseñas hagan match
        if (!password.equals(passwordConfirm)) {
            mEditPasswordConfirm.setError(getString(R.string.error_invalid_password_confirmation));
            mEditPasswordConfirm.requestFocus();
            return;
        }


        //Crea el usuario
        User user = new User(email, firstName, lastName, password, passwordConfirm);

        //Manda a crear al backend
        mBackendAccess.register(user, new BackendAccess.Callback() {

            @Override
            public void onRequestEnded(boolean success, String error) {
                if (success) {

                    //Abre la nueva activity
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    //Cierra la activity actual
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //endregion
}
