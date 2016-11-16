package com.fabian.tallernov2016.networking;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by bairon on 1/27/16.
 */
public class VolleyRequestSingleton {

    //region Singleton

    // Singleton instance
    private static VolleyRequestSingleton instance = null;

    /**
     * Get singleton instance
     *
     * @return singleton instance
     */
    public static synchronized VolleyRequestSingleton getInstance(Context pContext) {
        if (instance == null) {
            instance = new VolleyRequestSingleton(pContext);
        }
        return instance;
    }

    //endregion


    // region Attributes

    // Volley Queue

    private RequestQueue mQueue;

    // endregion


    // region Constructor

    /**
     * Constructor
     *
     * @param pContext
     */
    private VolleyRequestSingleton(Context pContext) {
        mQueue = Volley.newRequestQueue(pContext.getApplicationContext());
    }

    //endregion


    //region Queue request methods


    /**
     *
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
        JsonObjectRequest request = new JsonObjectRequest(method, url, body, listener, errorListener);

        //Add the request to the RequestQueue.
        mQueue.add(request);
    }

    //endregion
}
