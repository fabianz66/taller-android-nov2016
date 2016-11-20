package com.fabian.tallernov2016.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fabian on 11/6/16.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksHolder> {

    private List<Task> mTasks;

    /**
     * Se llama cada vez que se necesita crear un nuevo viewholder.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Se crea un view a partir de un xml con un Inflater.
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tasks, parent, false);

        //Se crea un viewholder que guarda referencia al view creado, para no tener que estar llamando al inflater
        //Cada vez.
        return new TasksHolder(rowView);
    }

    /**
     * Se llama cuando se necesita actualizar la informacion mostrada en un viewholder.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(TasksHolder holder, int position) {

        //Se busca el elemento que se va a mostrar
        Task taskToShow = mTasks.get(position);

        //Se actualiza la info que se esta mostrando en el viewHolder
        holder.mTitleView.setText(taskToShow.getTitle());
        holder.mDetailView.setText(taskToShow.getDetail());

        //Se actualiza la imagen
        ImageView imageHolder = holder.mImageView;
        Picasso.with(imageHolder.getContext()).load(taskToShow.getImageUrl()).into(imageHolder);
    }

    @Override
    public int getItemCount() {
        return mTasks != null ? mTasks.size() : 0;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }
}
