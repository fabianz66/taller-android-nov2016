package com.fabian.tallernov2016.models;

/**
 * Created by fabian on 11/6/16.
 */

public class Task {

    private String mTitle;
    private String mDetail;
    private String mImageUrl;

    public Task(String title, String detail, String imageUrl){
        mTitle = title;
        mDetail = detail;
        mImageUrl = imageUrl;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getDetail()
    {
        return mDetail;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }
}
