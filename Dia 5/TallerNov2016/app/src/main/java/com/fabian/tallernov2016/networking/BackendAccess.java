package com.fabian.tallernov2016.networking;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fabian.tallernov2016.models.Task;
import com.fabian.tallernov2016.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fabian on 11/6/16.
 */

public class BackendAccess {

    //region Constants

    private static final String HTTP_SCHEME = "http";
    private static final String AUTHORITY = "45.55.130.134:80";
    private static final String USERS_PATH = "users";
    private static final String USERS_LOGIN_PATH = "sign_in";
    private static final String USERS_LOGOUT_PATH = "sign_out";
    private static final String TASKS_PATH = "tasks";
    private static final String URL_EXTENSION = ".json";

    private static Uri.Builder getBuilder() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTP_SCHEME).encodedAuthority(AUTHORITY);
        return builder;
    }

    //endregion

    //region Callback

    public interface Callback {
        void onRequestEnded(boolean success, String error);
    }

    //endregion

    //region Atributos

    //Contexto del app
    private Context mContext;

    //endregion

    public BackendAccess(Context context) {
        mContext = context;
    }

    //region Users management

    public void login(User user, final Callback callback) {
        try {

            //Crea el cuerpo
            JSONObject container = new JSONObject();
            JSONObject body = new JSONObject();
            body.put(User.JSON_EMAIL, user.getEmail());
            body.put(User.JSON_PASSWORD, user.getPassword());
            container.put(User.JSON_USER, body);

            //Create URL:
            //http://45.55.130.134:3000/users/sign_in.json
            Uri.Builder builder = getBuilder();
            builder.appendPath(USERS_PATH);
            builder.appendPath(USERS_LOGIN_PATH);
            String url = builder.build().toString() + URL_EXTENSION;

            //Clear headers
            VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, container, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Guarda la info del usuario
                    User responseUser = User.fromJson(response);

                    //Guarda el usuario
                    Preferences.saveUser(mContext, responseUser);

                    //Guarda los headers
                    VolleyRequestSingleton.getInstance(mContext).saveHeadersForFutureRequests(responseUser);

                    //Notifica
                    callback.onRequestEnded(true, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Notifica
                    callback.onRequestEnded(false, error.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logout(final Callback callback) {

        //Create URL:
        //http://45.55.130.134:3000/users/sign_out.json
        Uri.Builder builder = getBuilder();
        builder.appendPath(USERS_PATH);
        builder.appendPath(USERS_LOGOUT_PATH);
        String url = builder.build().toString() + URL_EXTENSION;

        VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Borra el user de los Preferences
                Preferences.deleteUser(mContext);

                //Elimina los headers
                VolleyRequestSingleton.getInstance(mContext).clearHeaders();

                //Notifica
                callback.onRequestEnded(true, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Notifica
                callback.onRequestEnded(false, error.toString());
            }
        });
    }

    public void register(User user, final Callback callback) {
        try {

            //Crea el cuerpo
            JSONObject container = new JSONObject();
            JSONObject body = new JSONObject();
            body.put(User.JSON_EMAIL, user.getEmail());
            body.put(User.JSON_FIRST_NAME, user.getFirstName());
            body.put(User.JSON_LAST_NAME, user.getLastName());
            body.put(User.JSON_PASSWORD, user.getPassword());
            body.put(User.JSON_PASSWORD_CONFIRMATION, user.getPasswordConfirmation());
            container.put(User.JSON_USER, body);

            //Create URL
            //http://45.55.130.134:3000/users.json
            Uri.Builder builder = getBuilder();
            builder.appendPath(USERS_PATH);
            String url = builder.build().toString() + URL_EXTENSION;

            //Manda el request utilizando volley
            VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, container, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Guarda la info del usuario
                    User responseUser = User.fromJson(response);

                    //Guarda el usuario
                    Preferences.saveUser(mContext, responseUser);

                    //Guarda los headers
                    VolleyRequestSingleton.getInstance(mContext).saveHeadersForFutureRequests(responseUser);

                    //Notifica
                    callback.onRequestEnded(true, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Notifica
                    callback.onRequestEnded(false, error.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //endregion


    //region Tasks

    public void createTask(Task task, final Callback callback) {

        //Create URL
        //http://45.55.130.134:3000/tasks.json
        Uri.Builder builder = getBuilder();
        builder.appendPath(TASKS_PATH);
        String url = builder.build().toString() + URL_EXTENSION;

        //Create body
        JSONObject body = task.toJson();

        //Agrega el request al queue de Volley
        VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onRequestEnded(true, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onRequestEnded(false, error.toString());
            }
        });
    }

    public void indexTasks(final List<Task> result, final Callback callback) {

        //Create URL
        //http://45.55.130.134:3000/tasks.json
        Uri.Builder builder = getBuilder();
        builder.appendPath(TASKS_PATH);
        String url = builder.build().toString() + URL_EXTENSION;

        //Agrega el request al queue de Volley
        VolleyRequestSingleton.getInstance(mContext).addJsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Task.fromJsonArray(response, result);
                callback.onRequestEnded(true, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onRequestEnded(false, error.toString());
            }
        });
    }

    //endregion
}
