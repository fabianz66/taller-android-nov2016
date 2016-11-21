package com.fabian.tallernov2016.models;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabian on 11/13/16.
 */

public class Task {

    //region Constants

    public static final String JSON_TASK = "task";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DETAIL = "detail";
    public static final String JSON_IMAGE = "attachment";
    public static final String JSON_IMAGE_URL = "attachment_url";

    //endregion

    private String mTitle;
    private String mDetail;
    private Bitmap mImage;
    private String mImageUrl;

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

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public void setImageUrl(String url) {
        mImageUrl = url;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getImageBase64() {
        if(mImage != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String base64Image = "data:image/png;base64, " + Base64.encodeToString(byteArray, Base64.DEFAULT);
            return base64Image;
        }
        return null;
    }

    public JSONObject toJson() {

        try {

            //Container
            JSONObject container = new JSONObject();

            //Cuerpo con la informacion
            JSONObject body = new JSONObject();

            //Titulo y detalle siempre deben exitir
            body.put(JSON_TITLE, mTitle);
            body.put(JSON_DETAIL, mDetail);

            //Guarda la imagen solo si existe
            String imageBase64 = getImageBase64();
            if(imageBase64 != null) {
                body.put(JSON_IMAGE, imageBase64);
            }

            //Guarda la info en el container
            container.put(JSON_TASK, body);
            return container;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static com.fabian.tallernov2016.models.Task fromJson(JSONObject json) {

        //Lee titulo y detalle
        String title = json.optString(JSON_TITLE, "Unknown");
        String detail = json.optString(JSON_DETAIL, "Unknown");
        com.fabian.tallernov2016.models.Task task = new com.fabian.tallernov2016.models.Task(title, detail);

        //Lee el url de la imagen. Si la imagen no existe, se devuelve un URL default.
        String imageUrl = json.optString(JSON_IMAGE_URL, null);
        task.setImageUrl(imageUrl);
        return task;
    }

    public static void fromJsonArray(JSONArray array, List<com.fabian.tallernov2016.models.Task> result) {
        int count = array.length();
        for (int i = 0; i < count; i++) {
            try {
                com.fabian.tallernov2016.models.Task task = fromJson(array.getJSONObject(i));
                result.add(task);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<com.fabian.tallernov2016.models.Task> dummyData() {
        ArrayList<com.fabian.tallernov2016.models.Task> list = new ArrayList<>();
        list.add(new com.fabian.tallernov2016.models.Task("Comprar leche","0% grasa"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar pan","Integral"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar helados","De yogurt"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar leche","0% grasa"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar pan","Integral"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar helados","De yogurt"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar leche","0% grasa"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar pan","Integral"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar helados","De yogurt"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar leche","0% grasa"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar pan","Integral"));
        list.add(new com.fabian.tallernov2016.models.Task("Comprar helados","De yogurt"));
        return list;
    }
}
