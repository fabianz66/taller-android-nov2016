package com.fabian.tallernov2016.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fabian on 11/6/16.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksHolder> {

    //region POr agregar


    public interface onTaskClickListener {
        void onTaskSelected(Task task);
    }

    private List<Task> mTasks;
    private onTaskClickListener mListener;

    public TasksAdapter(onTaskClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        if(mTasks == null) {
            return 0;
        }
        return mTasks.size();
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

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
    public void onBindViewHolder(TasksHolder holder, final int position) {

        //Se busca el elemento que se va a mostrar
        Task taskToShow = mTasks.get(position);

        //Se actualiza la info que se esta mostrando en el viewHolder
        holder.mTitleView.setText(taskToShow.getTitle());
        holder.mDetailView.setText(taskToShow.getDetail());

        //Se actualiza la imagen
        ImageView imageHolder = holder.mImageView;
        final Context context = imageHolder.getContext();
        Picasso.with(context).load(taskToShow.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(imageHolder);

        //Click a la celda
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task selectedTask = mTasks.get(position);
                mListener.onTaskSelected(selectedTask);

            }
        });
    }




}
