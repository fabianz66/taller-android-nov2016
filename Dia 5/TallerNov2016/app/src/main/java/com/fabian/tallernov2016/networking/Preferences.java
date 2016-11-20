package com.fabian.tallernov2016.networking;

import android.content.Context;
import android.content.SharedPreferences;

import com.fabian.tallernov2016.R;
import com.fabian.tallernov2016.models.User;

/**
 * Created by fabian on 11/20/16.
 */

public class Preferences {

    //region Constants

    private static final String PREF_FILE_KEY = "user_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_EMAIL = "email";

    //endregion

    public static void saveUser(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_AUTH_TOKEN, user.getAuthToken());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    public static void deleteUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(KEY_AUTH_TOKEN);
        editor.remove(KEY_EMAIL);
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        String authToken = sharedPref.getString(KEY_AUTH_TOKEN, null);
        String email = sharedPref.getString(KEY_EMAIL, null);
        if(authToken == null || email == null) {
            return null;
        }
        User user = new User();
        user.setAuthToken(authToken);
        user.setEmail(email);
        return user;
    }
}
