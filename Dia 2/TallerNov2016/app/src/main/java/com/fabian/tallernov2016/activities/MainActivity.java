package com.fabian.tallernov2016.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fabian.tallernov2016.AppContext;
import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.fragments.TasksFragment;
import com.fabian.tallernov2016.models.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region Ciclo de vida

    /**
     * Metodo que se llama cuando la Actividad es creada por primera vez
     * Aqui se debe establecer el view que va a mostrar y configurar
     * de acuerdo a las necesidades.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Establece el view que se va a mostrar en esta activity
        setContentView(R.layout.activity_main);

        //Configura el menu que muestra las secciones del app
        setupNavigation();

        //Muestra la info del usuario en el header
        setupNavigationHeader();
    }

    //endregion

    /**
     * Configura el drawer menu.
     * - Establece el toolbar como Action bar de esta Activity
     * - Agrega el hamburguer menu para abrir/cerrar el menu.
     * - Agrega un listener para detectar cuando se cambia de seccion en el menu.
     */
    private void setupNavigation() {

        //Se toma el toolbar del layout y se establece como el toolbar
        //de la Activity.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Crea el hamburguer button que se muestra en el toolbar para abrir/cerrar el Drawer Menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
    private void setupNavigationHeader()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView headerUserName = (TextView) header.findViewById(R.id.nav_header_username);
        TextView headerUserEmail = (TextView) header.findViewById(R.id.nav_header_email);
        User user = ((AppContext)getApplication()).getUser();
        headerUserName.setText(user.getName());
        headerUserEmail.setText(user.getEmail());
    }


    /**
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
        }

        //Muestra el fragment
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        //Cierra el menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
