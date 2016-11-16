package com.fabian.tallernov2016.activities;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fabian.tallernov2016.AppContext;
import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.fragments.TasksFragment;
import com.fabian.tallernov2016.models.User;
import com.fabian.tallernov2016.networking.UsersBackendAccess;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region Atributos

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    UsersBackendAccess mUsersBackendAccess;

    //endregion

    //region Ciclo de vida

    /**
     * Metodo que se llama cuando la Actividad es creada por primera vez
     * Aqui se debe establecer el view que va a mostrar y configurar
     * de acuerdo a las necesidades.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Establece el view que se va a mostrar en esta activity
        setContentView(R.layout.activity_main);
        mUsersBackendAccess = new UsersBackendAccess((AppContext) getApplication());

        //Configura el menu que muestra las secciones del app
        setupNavigation();

        //Muestra la info del usuario en el header
        setupNavigationHeader();

        //Agrega un listener al backstack de fragments para saber cuando hay que devolverse a una ventana anterior
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                //Si el numero de fragments es mayor a 0, significa que hay un fragment encima de otro
                //Y que el app deberia devolverse.
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    mDrawerToggle.setDrawerIndicatorEnabled(false);
                } else {
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //Sincroniza el estado del toggle
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //endregion

    /**
     * Configura el drawer menu.
     * - Establece el toolbar como Action bar de esta Activity
     * - Agrega el hamburguer menu para abrir/cerrar el menu.
     * - Agrega un listener para detectar cuando se cambia de seccion en el menu.
     */
    private void setupNavigation() {

        //Se toma el toolbar del layout y se establece como el toolbar de la Activity.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Crea el hamburguer button que se muestra en el toolbar para abrir/cerrar el Drawer Menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.navigation_drawer_open, /* "open drawer" description */
                R.string.navigation_drawer_close); /* "close drawer" description */
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //Asigna un listener para cuando se selecciona una seccion del menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Muestra la seccion de tareas por default
        navigationView.setCheckedItem(R.id.nav_tareas);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_tareas));
    }


    /**
     * Muestra la info del usuario en el header del navigation view
     */
    private void setupNavigationHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView headerUserName = (TextView) header.findViewById(R.id.nav_header_username);
        TextView headerUserEmail = (TextView) header.findViewById(R.id.nav_header_email);
        User user = ((AppContext) getApplication()).getUser();
        headerUserName.setText(user.getName());
        headerUserEmail.setText(user.getEmail());
    }


    /**
     * Metodo que se llama cuando se selecciona una secccion del Drawer Menu
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Fragment por mostrar
        Fragment fragment = null;

        //Muestra el fragment necesario
        switch (item.getItemId()) {
            case R.id.nav_tareas:
                fragment = new TasksFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        //Muestra el fragment
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        //Cierra el menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Primero se debe verificar si el drawer toggle usa este metodo
        //Si no lo utiliza podemos comenzar con nuestra propia logica
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //Revisamos el id de la accion que se hizo click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //region User backend access

    private void logout()
    {
        mUsersBackendAccess.logout(new UsersBackendAccess.Callback() {
            @Override
            public void onRequestEnded(boolean success, String error) {
                if(success) {
                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //endregion
}
