package com.fabian.tallernov2016.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabian on 11/6/16.
 */

public class User {

    //region Constants

    public static final String JSON_USER = "user";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_FIRST_NAME = "first_name";
    public static final String JSON_LAST_NAME = "last_name";
    public static final String JSON_PASSWORD = "password";
    public static final String JSON_PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String JSON_AUTHENTICATION_TOKEN = "authentication_token";

    //endregion

    //region Atributos

    private String mAuthToken;
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private String mPassword;
    private String mPasswordConfirmation;

    //endregion

    //region Contructor

    public User() {

    }

    public User(String email, String password)
    {
        mEmail = email;
        mPassword = password;
    }

    public User(String email, String firstName, String lastName, String password, String passwordConfirmation)
    {
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
        mPassword = password;
        mPasswordConfirmation = passwordConfirmation;
    }

    //endregion

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) { mEmail = email; }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) { mFirstName = firstName; }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) { mLastName = lastName; }

    public String getPassword() {return mPassword;}

    public String getPasswordConfirmation() {return mPasswordConfirmation;}

    public String getAuthToken() {return mAuthToken; }

    public void setAuthToken(String authToken) { mAuthToken = authToken; }

    public String getName() {
        return String.format("%s %s", mFirstName, mLastName);
    }

    //region JSON

    public static User fromJson(JSONObject json)
    {
        User user = new User();
        user.setEmail(json.optString(JSON_EMAIL));
        user.setFirstName(json.optString(JSON_FIRST_NAME));
        user.setLastName(json.optString(JSON_LAST_NAME));
        user.setAuthToken(json.optString(JSON_AUTHENTICATION_TOKEN));
        return user;
    }

    //endregion
}
