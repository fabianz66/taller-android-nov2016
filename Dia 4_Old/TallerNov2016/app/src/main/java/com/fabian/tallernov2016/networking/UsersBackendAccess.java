package com.fabian.tallernov2016.networking;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fabian.tallernov2016.AppContext;
import com.fabian.tallernov2016.models.User;
import com.fabian.tallernov2016.networking.volley.RequestHeaders;
import com.fabian.tallernov2016.networking.volley.VolleyRequestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabian on 11/6/16.
 */

public class UsersBackendAccess extends BackendAccess {

    //Callback para las llamadas que realiza esta clase

    public interface Callback {
        void onRequestEnded(boolean success, String error);
    }

    //region Constantes

    private static final String USERS_PATH = "users";
    private static final String USERS_LOGIN_PATH = "sign_in";
    private static final String USERS_LOGOUT_PATH = "sign_out";

    //endregion

    //region Constructor

    public UsersBackendAccess(AppContext context) {
        super(context);
    }

    //endregion

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
            Uri.Builder builder = getBuilder();
            builder.appendPath(USERS_PATH);
            String url = builder.build().toString() + URL_EXTENSION;

            //Manda el request utilizando volley
            VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, container, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Crea un usuario a partir de la respuesta
                    User createdUser = User.fromJson(response);

                    //Guarda el usuario en el context
                    mContext.setUser(createdUser);

                    //Guarda los headers para los proximos requests
                    RequestHeaders.addUserHeaders(createdUser);

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

    public void login(User user, final Callback callback) {
        try {

            //Crea el cuerpo
            JSONObject container = new JSONObject();
            JSONObject body = new JSONObject();
            body.put(User.JSON_EMAIL, user.getEmail());
            body.put(User.JSON_PASSWORD, user.getPassword());
            container.put(User.JSON_USER, body);

            //Create URL
            Uri.Builder builder = getBuilder();
            builder.appendPath(USERS_PATH);
            builder.appendPath(USERS_LOGIN_PATH);
            String url = builder.build().toString() + URL_EXTENSION;

            //Manda el request utilizando volley
            VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.POST, url, container, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Crea un usuario a partir de la respuesta
                    User createdUser = User.fromJson(response);

                    //Guarda el usuario en el context
                    mContext.setUser(createdUser);

                    //Guarda los headers para los proximos requests
                    RequestHeaders.addUserHeaders(createdUser);

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

        //Create URL
        Uri.Builder builder = getBuilder();
        builder.appendPath(USERS_PATH);
        builder.appendPath(USERS_LOGOUT_PATH);
        String url = builder.build().toString() + URL_EXTENSION;

        //Manda el request utilizando volley
        VolleyRequestSingleton.getInstance(mContext).addJsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Borra el usuario en el context
                mContext.setUser(null);

                //Borra los headers
                RequestHeaders.clearHeaders();

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

}
