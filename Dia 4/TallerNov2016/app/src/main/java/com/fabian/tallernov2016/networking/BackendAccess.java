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

import java.util.List;

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
}
