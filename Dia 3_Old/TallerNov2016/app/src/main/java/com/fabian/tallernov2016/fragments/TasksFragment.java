package com.fabian.tallernov2016.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.adapters.TasksAdapter;

/**
 * Created by fabian on 11/6/16.
 */

public class TasksFragment extends Fragment {

    //region Atributos

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //enregion

    //region Ciclo de vida

    @Override
    public void onResume() {
        super.onResume();

        //Cambia el titulo de la ventana
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.title_tasks_screen);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Crea el view a partir del XML
        View view = inflater.inflate(R.layout.fragment_tasks, null);

        //Configura la lista
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tasks_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TasksAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //Configura la accion del boton para agregar nueva tarea
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateTaskFragment();
            }
        });
        return view;
    }

    //endregion

    //region Acciones de UI

    private void showCreateTaskFragment()
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CreateTaskFragment()).addToBackStack(null).commit();
    }
}
