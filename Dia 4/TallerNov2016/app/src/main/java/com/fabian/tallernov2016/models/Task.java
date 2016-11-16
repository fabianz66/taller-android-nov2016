package com.fabian.tallernov2016.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian on 11/13/16.
 */

public class Task {

    private String mTitle;
    private String mDetail;

    public Task(String title, String detail) {
        mTitle = title;
        mDetail = detail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetail() {
        return mDetail;
    }

    public static List<Task> dummyData() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(new Task("Comprar leche","0% grasa"));
        list.add(new Task("Comprar pan","Integral"));
        list.add(new Task("Comprar helados","De yogurt"));
        list.add(new Task("Comprar leche","0% grasa"));
        list.add(new Task("Comprar pan","Integral"));
        list.add(new Task("Comprar helados","De yogurt"));
        list.add(new Task("Comprar leche","0% grasa"));
        list.add(new Task("Comprar pan","Integral"));
        list.add(new Task("Comprar helados","De yogurt"));
        list.add(new Task("Comprar leche","0% grasa"));
        list.add(new Task("Comprar pan","Integral"));
        list.add(new Task("Comprar helados","De yogurt"));
        return list;
    }
}
