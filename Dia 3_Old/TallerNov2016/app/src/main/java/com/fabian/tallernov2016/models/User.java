package com.fabian.tallernov2016.models;

/**
 * Created by fabian on 11/6/16.
 */

public class User {

    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public User(String email, String firstName, String lastName)
    {
        mEmail = email;
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getName() {
        return String.format("%s %s", mFirstName, mLastName);
    }
}
