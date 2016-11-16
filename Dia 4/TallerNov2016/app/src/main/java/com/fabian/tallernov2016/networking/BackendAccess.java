package com.fabian.tallernov2016.networking;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fabian.tallernov2016.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabian on 11/6/16.
 */

public class BackendAccess {

    //region Constants

    private static final String HTTP_SCHEME = "http";
    private static final String AUTHORITY = "45.55.130.134:3000";
    private static final String USERS_PATH = "users";
    private static final String USERS_LOGIN_PATH = "sign_in";
    private static final String USERS_LOGOUT_PATH = "sign_out";
    private static final String URL_EXTENSION = ".json";

    static Uri.Builder getBuilder() {

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

    Context mContext;

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

            //Manda el request utilizando volley
            VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, container, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

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
}
