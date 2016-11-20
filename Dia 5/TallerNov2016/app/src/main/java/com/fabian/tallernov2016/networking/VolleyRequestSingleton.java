package com.fabian.tallernov2016.networking;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fabian.tallernov2016.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bairon on 1/27/16.
 */
public class VolleyRequestSingleton {

    //region Singleton

    // Singleton sInstance
    private static VolleyRequestSingleton sInstance = null;

    /**
     * Get singleton sInstance
     *
     * @return singleton sInstance
     */
    public static synchronized VolleyRequestSingleton getInstance(Context pContext) {
        if (sInstance == null) {
            sInstance = new VolleyRequestSingleton(pContext);
        }
        return sInstance;
    }

    /**
     * Constructor
     *
     * @param pContext
     */
    private VolleyRequestSingleton(Context pContext) {
        mQueue = Volley.newRequestQueue(pContext.getApplicationContext());
        mHeaders = new HashMap<>();
    }

    //endregion


    // region Attributes

    // Volley Queue

    private RequestQueue mQueue;
    private Map<String, String> mHeaders;

    // endregion


    //region Queue request methods


    /**
     * @param method PUT, GET, POST, UPDATE, DELETE
     * @param url El url del servicio
     * @param body El cuerpo que se debe enviar cm JSON
     * @param listener Objeto a notificar cuando el request finalizo
     * @param errorListener Objecto a notificat cuando el request fallo
     */
    public void addJsonObjectRequest(int method,
                                     String url,
                                     JSONObject body,
                                     Response.Listener<JSONObject> listener,
                                     Response.ErrorListener errorListener)
    {
        //Create request
        JsonObjectRequest request = new JsonObjectRequest(method, url, body, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaders;
            }
        };

        //Add the request to the RequestQueue.
        mQueue.add(request);
    }

    /**
     * @param method PUT, GET, POST, UPDATE, DELETE
     * @param url El url del servicio
     * @param params El cuerpo que se debe enviar cm JSON
     * @param listener Objeto a notificar cuando el request finalizo
     * @param errorListener Objecto a notificat cuando el request fallo
     */
    public void addJsonArrayRequest(int method,
                                     String url,
                                     JSONArray params,
                                     Response.Listener<JSONArray> listener,
                                     Response.ErrorListener errorListener)
    {
        //Create request
        JsonArrayRequest request = new JsonArrayRequest(method, url, params, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaders;
            }
        };

        //Add the request to the RequestQueue.
        mQueue.add(request);
    }

    /**
     *  Guarda los headers que se necesitan enviar con cada request cuando un usuario tiene la
     *  sesion iniciada.
     * @param user El usuario que acaba de iniciar sesion.
     */
    public void saveHeadersForFutureRequests(User user){
        mHeaders.put("X-User-Email", user.getEmail());
        mHeaders.put("X-User-Token", user.getAuthToken());
    }

    /**
     * Borra los headers guardados previamente con saveHeadersForFutureRequests()
     */
    public void clearHeaders() {
        mHeaders.clear();
    }

    //endregion
}
