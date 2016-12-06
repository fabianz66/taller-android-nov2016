package com.fabian.tallernov2016.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.Task;

/**
 * Created by fabian on 11/23/16.
 */

public class TaskInfoFragment extends Fragment {

    public Task mSelectedTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.task_info_fragment, null);

        Bundle bundle = getArguments();
        mSelectedTask = (Task) bundle.getSerializable("TASK");
        if(mSelectedTask != null) {
            Toast.makeText(getContext(), "Selected task: " + mSelectedTask.getTitle(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "No hay task", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }
}
