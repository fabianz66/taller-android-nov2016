package com.fabian.tallernov2016.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fabian.tallernov2016.R;

/**
 * Created by fabian on 11/13/16.
 */
public class TasksHolder extends RecyclerView.ViewHolder {

    public TextView mTitleView;
    public TextView mDetailView;
    public TasksHolder(View rowView) {
        super(rowView);
        mTitleView = (TextView) rowView.findViewById(R.id.rowTitle);
        mDetailView = (TextView) rowView.findViewById(R.id.rowDetail);
    }
}
