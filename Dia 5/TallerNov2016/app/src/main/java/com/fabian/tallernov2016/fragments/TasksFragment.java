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
import android.widget.Toast;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.Task;
import com.fabian.tallernov2016.networking.BackendAccess;
import com.fabian.tallernov2016.recyclerview.TasksAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian on 11/13/16.
 */

public class TasksFragment extends Fragment implements TasksAdapter.onTaskClickListener {

    private BackendAccess mBackendAccess;
    private TasksAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();

        //Cambia el titulo de la ventana
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(R.string.title_tasks_screen);
        }

        //Carga la lista para este usuario desde el backend
        if(mAdapter != null) {
            indexTasks();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBackendAccess = new BackendAccess(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, null);

        //Se obtiene el recycler view, se le asigna el layout manager y el adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new TasksAdapter(this);

        recyclerView.setAdapter(mAdapter);

        //Configura la accion del boton para agregar nueva tarea
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateTaskFragment();
            }
        });
        return view;
    }

    //region Acciones de UI

    private void showCreateTaskFragment()
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CreateTaskFragment()).addToBackStack(null).commit();
    }

    private void indexTasks() {
        final List<Task> tasks = new ArrayList<>();
        mBackendAccess.indexTasks(tasks, new BackendAccess.Callback() {
            @Override
            public void onRequestEnded(boolean success, String error) {
                if(success) {
                    mAdapter.setTasks(tasks);
                    mAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onTaskSelected(Task task) {

        FragmentTransaction trans = getFragmentManager().beginTransaction();

        TaskInfoFragment fragment = new TaskInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("TASK", task);
        fragment.setArguments(bundle);

        trans.replace(R.id.fragment_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

    //endregion
}
