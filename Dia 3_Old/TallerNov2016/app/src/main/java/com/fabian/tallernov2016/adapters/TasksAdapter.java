package com.fabian.tallernov2016.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.Task;

/**
 * Created by fabian on 11/6/16.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    //region View Holder

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView mTitleView;
        public TextView mDetailView;
        public ViewHolder(View rowView) {
            super(rowView);
            mTitleView = (TextView) rowView.findViewById(R.id.rowTitle);
            mDetailView = (TextView) rowView.findViewById(R.id.rowDetail);
        }
    }

    //endregion

    //region Atributos

    private Task[] mTasks;

    //endregion

    //region Contructor

    public TasksAdapter() {
        mTasks = new Task[1];
        mTasks[0] = new Task("Comprar helado", "De Naranja holandesa", null);
    }

    //endregion


    //region Adapter callbacks

    // Create new views (invoked by the layout manager)
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view holder
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tasks, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {
        Task currentTask = mTasks[position];
        holder.mTitleView.setText(currentTask.getTitle());
        holder.mDetailView.setText(currentTask.getDetail());
    }

    @Override
    public int getItemCount() {
        return mTasks.length;
    }

    //endregion
}
